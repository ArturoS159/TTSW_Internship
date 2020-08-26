package pl.com.ttsw.intership.wholesaleserverapp.rest.service.impl;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.github.millij.poi.SpreadsheetReadException;
import lombok.AllArgsConstructor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.AddItemToOrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.BasketItemDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.TotalElements;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper.BasketItemMapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper.OrderMapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.mongo.ProductRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.BasketItemRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.OrderRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.WarehouseRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.BasketService;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.FileService;
import pl.com.ttsw.intership.wholesaleserverapp.rest.specification.BasketItemSpecification;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.opencsv.ICSVWriter.DEFAULT_SEPARATOR;
import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;
import static java.util.Objects.nonNull;
import static pl.com.ttsw.intership.wholesaleserverapp.rest.statics.Static.*;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService, FileService {

    private final OrderRepository orderRepository;
    private final BasketItemRepository basketItemRepository;
    private final UserRepository userRepository;
    private final BasketItemMapper basketItemMapper;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final OrderMapper orderMapper;


    @Override
    public void addBasketToOrder(AddItemToOrderDto addItemToOrderDto, Long userId, LocalDate data) {
        final Product product = productRepository.findProductByWarehouseAndIdOrName(
                addItemToOrderDto.getWarehouse(),
                addItemToOrderDto.getProductId(),
                null).orElseThrow(ProductNotFoundException::new);
        addBasketItemToOrder(addItemToOrderDto, product, userId, data);
    }

    private void addBasketItemToOrder(AddItemToOrderDto addItemToOrderDto, Product product, Long userId, LocalDate data) {
        if (addItemToOrderDto.getAmount() <= ZERO) {
            throw new IllegalArgumentException();
        }
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        final Warehouse warehouse = warehouseRepository
                .findByWarehouseName(product.getWarehouse())
                .orElseThrow(WarehouseNotFoundException::new);

        final Order order = orderRepository
                .findByUserAndWarehouseWarehouseNameAndActive(user, warehouse.getWarehouseName(), true)
                .orElse(orderMapper.toOrder(product, user, warehouse, data));
        orderRepository.save(order);

        BasketItem basketItem = basketItemRepository.
                findByNameAndWarehouseAndUser(product.getName(), warehouse.getWarehouseName(), user)
                .orElse(basketItemMapper.toBasketItem(product, order));

        basketItem.setAmount(basketItem.getAmount() + addItemToOrderDto.getAmount());
        order.setTotalPrice(order.getTotalPrice().add(BigDecimal.valueOf(addItemToOrderDto.getAmount()).multiply(product.getPrice())));
        order.setTotalAmount(order.getTotalAmount() + addItemToOrderDto.getAmount());
        basketItemRepository.save(basketItem);
    }

    @Override
    public void delBasketItemFromOrder(Long orderId, Long basketItemId) {
        Order order = orderRepository.findByOrderIdAndActive(orderId, true).orElseThrow(OrderNotFoundException::new);
        final BasketItem basketItem = basketItemRepository.findByBasketIdAndOrder(basketItemId, order).orElseThrow(BasketItemNotFoundException::new);
        order.setTotalPrice(
                order.getTotalPrice().subtract(
                        basketItem.getPrice().multiply(BigDecimal.valueOf(basketItem.getAmount()))
                )
        );
        order.setTotalAmount(order.getTotalAmount() - basketItem.getAmount());
        orderRepository.save(order);
        basketItemRepository.delete(basketItem);
    }

    @Override
    public void updateBasketItem(Long basketItemId, Long amount) {
        BasketItem basketItem = basketItemRepository.findById(basketItemId).orElseThrow(BasketItemNotFoundException::new);
        if (amount > ZERO) {
            final long diffAmount = basketItem.getAmount() - amount;
            basketItem.setAmount(amount);
            basketItem.getOrder().setTotalAmount(basketItem.getOrder().getTotalAmount() - diffAmount);
            basketItem.getOrder().setTotalPrice(basketItem.getOrder().getTotalPrice().subtract(BigDecimal.valueOf(diffAmount).multiply(basketItem.getPrice())));
            basketItemRepository.save(basketItem);
        }
    }

    @Override
    public Page<BasketItemDto> getAllProductInBasket(Long userId, BasketItemDto basketItemDto, Pageable pageable) {
        basketItemDto.setUserId(userRepository.findById(userId).orElseThrow(UserNotFoundException::new).getUserId());
        final Specification<BasketItem> specification = new BasketItemSpecification(basketItemDto);
        return basketItemRepository.findAll(specification, pageable).map(basketItemMapper::toBasketItemDto);
    }

    @Override
    public TotalElements getTotalElements(Long userId) {
        TotalElements totalElements = new TotalElements();
        totalElements.setTotalAmount(basketItemRepository.findSumAmount(userId));
        totalElements.setTotalPrice(basketItemRepository.findTotalPrice(userId));
        totalElements.setWarehouseList(basketItemRepository.findAllWarehouseByUser(userId));
        totalElements.setManufactureList(basketItemRepository.findAllManufactureByUser(userId));
        totalElements.setMinMaxList(Arrays.asList(
                basketItemRepository.findMinPrice(userId).orElse(BigDecimal.ZERO),
                basketItemRepository.findMaxPrice(userId).orElse(BigDecimal.ZERO)));
        return totalElements;
    }

    @Override
    public void toCsv(Long userId, HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        ColumnPositionMappingStrategy<BasketItemDto> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(BasketItemDto.class);
        strategy.setColumnMapping(HEADER);

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + FILENAME + LocalDate.now() + ".csv\"");

        StatefulBeanToCsv<BasketItemDto> writer = new StatefulBeanToCsvBuilder<BasketItemDto>(response.getWriter())
                .withQuotechar(NO_QUOTE_CHARACTER)
                .withSeparator(DEFAULT_SEPARATOR)
                .withMappingStrategy(strategy)
                .withOrderedResults(false)
                .build();
        final BasketItemSpecification spec = getBasketItemSpecification(userId);
        List<BasketItemDto> basketItemDtoList = basketItemMapper.toBasketItemListDto(basketItemRepository.findAll(spec));

        if (CollectionUtils.isEmpty(basketItemDtoList)) {
            throw new BasketItemNotFoundException();
        }
        writer.write(basketItemDtoList);
    }

    private BasketItemSpecification getBasketItemSpecification(Long userId) {
        BasketItemDto tmp = new BasketItemDto();
        tmp.setUserId(userId);
        return new BasketItemSpecification(tmp);
    }

    @Override
    public List<String> setBasketFromCsv(Long userId, MultipartFile file) throws IOException {
        ColumnPositionMappingStrategy<AddItemToOrderDto> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(AddItemToOrderDto.class);
        strategy.setColumnMapping(HEADER);

        try (InputStream is = file.getInputStream(); BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            Iterator<AddItemToOrderDto> addItemToOrderDtoIterator = new CsvToBeanBuilder<AddItemToOrderDto>(br)
                    .withSeparator(DEFAULT_SEPARATOR)
                    .withMappingStrategy(strategy)
                    .withOrderedResults(false)
                    .build()
                    .iterator();

            List<String> productAddedList = new ArrayList<>();
            while (addItemToOrderDtoIterator.hasNext()) {
                AddItemToOrderDto addItemToOrderDto = addItemToOrderDtoIterator.next();
                addProductToBasket(userId, productAddedList, addItemToOrderDto);
            }
            return productAddedList;
        }
    }

    @Override
    public ByteArrayInputStream toXlxs(Long userId, HttpServletResponse response, boolean group) throws IOException {
        try (Workbook workbook = new SXSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            response.setHeader("Content-Disposition", "attachment; filename=" + FILENAME + LocalDate.now() + ".xlsx");

            List<BasketItemDto> basketItemDtoList = basketItemMapper.toBasketItemListDto(
                    basketItemRepository.findAllByOrderUserUserIdAndOrderActive(userId, true, Sort.by(Sort.Direction.ASC, "order.warehouse.warehouseName"))
            );

            if (CollectionUtils.isEmpty(basketItemDtoList)) {
                throw new BasketItemNotFoundException();
            }
            Sheet activeSheet = workbook.createSheet(basketItemDtoList.get(0).getWarehouse());
            Row r = activeSheet.createRow(ZERO);
            setHeadersXlxs(r);
            int rowCount = ONE;
            if (group) {
                for (BasketItemDto basketItem : basketItemDtoList) {
                    if (nonNull(workbook.getSheet(basketItem.getWarehouse()))) {
                        activeSheet = workbook.getSheet(basketItem.getWarehouse());
                    } else {
                        activeSheet = workbook.createSheet(basketItem.getWarehouse());
                        Row rs = activeSheet.createRow(ZERO);
                        setHeadersXlxs(rs);
                        rowCount = ONE;
                    }
                    writeRowBasketItem(basketItem, activeSheet, rowCount);
                    rowCount++;
                }
            } else {
                for (BasketItemDto basketItem : basketItemDtoList) {
                    writeRowBasketItem(basketItem, activeSheet, rowCount);
                    rowCount++;
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private void setHeadersXlxs(Row r) {
        for (int rowNum = ZERO; rowNum < HEADER.length; rowNum++) {
            r.createCell(rowNum).setCellValue(HEADER[rowNum].toUpperCase());
        }
    }

    private void writeRowBasketItem(BasketItemDto basketItem, Sheet sheet, int rowNum) {
        Row row = sheet.createRow(rowNum);
        row.createCell(ZERO).setCellValue(basketItem.getWarehouse());
        row.createCell(ONE).setCellValue(basketItem.getName());
        row.createCell(TWO).setCellValue(basketItem.getManufacturer());
        row.createCell(THREE).setCellValue(basketItem.getPrice().toString());
        row.createCell(FOUR).setCellValue(basketItem.getAmount());
    }

    @Override
    public List<String> setBasketFromXlxs(Long userId, MultipartFile file) throws IOException, SpreadsheetReadException, OpenXML4JException {
        try (InputStream is = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            List<String> productAddedList = new ArrayList<>();

            for (int numOfSheet = ZERO; numOfSheet < workbook.getNumberOfSheets(); numOfSheet++) {
                Iterator<Row> it = workbook.getSheetAt(numOfSheet).iterator();
                it.next();
                while (it.hasNext()) {
                    Row row = it.next();
                    addProductToBasket(userId, productAddedList, new AddItemToOrderDto(row));
                }
            }
            return productAddedList;
        }
    }

    private void addProductToBasket(Long userId, List<String> productAddedList, AddItemToOrderDto addItemToOrderDto) {
        Optional<Product> product = productRepository.findProductByWarehouseAndIdOrName(
                addItemToOrderDto.getWarehouse(),
                addItemToOrderDto.getProductId(),
                addItemToOrderDto.getName());

        product.ifPresent(productVal -> {
            addBasketItemToOrder(addItemToOrderDto, productVal, userId, null);
            productAddedList.add(product.get().getName());
        });
    }
}
