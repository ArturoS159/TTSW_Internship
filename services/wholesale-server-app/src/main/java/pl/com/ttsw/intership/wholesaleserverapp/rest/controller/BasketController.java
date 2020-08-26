package pl.com.ttsw.intership.wholesaleserverapp.rest.controller;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.github.millij.poi.SpreadsheetReadException;
import lombok.AllArgsConstructor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.AddItemToOrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.BasketItemDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.TotalElements;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.BasketService;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest-service/basket")
@CrossOrigin("*")
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final FileService fileService;

    @PreAuthorize("#userId == principal.id")
    @PostMapping("/{userId}/add")
    public ResponseEntity<Void> addBasketItemToOrder(@RequestBody AddItemToOrderDto addItemToOrderDto,
                                                     @PathVariable Long userId,
                                                     @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        basketService.addBasketToOrder(addItemToOrderDto, userId, date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @DeleteMapping("{userId}/{orderId}/del/{basketItemId}")
    public ResponseEntity<Void> deleteBasketItemFromOrder(@PathVariable Long userId,
                                                          @PathVariable Long orderId,
                                                          @PathVariable Long basketItemId) {
        basketService.delBasketItemFromOrder(orderId, basketItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @PutMapping("/{userId}/{basketItemId}/update")
    public ResponseEntity<Void> updateBasketItemFromOrder(@PathVariable Long userId,
                                                          @PathVariable Long basketItemId,
                                                          @RequestParam("amount") Long amount) {
        basketService.updateBasketItem(basketItemId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @GetMapping("/{userId}/all")
    public ResponseEntity<Page<BasketItemDto>> getAllProductInBasket(@PathVariable Long userId,
                                                                     @ModelAttribute("basketItemDto") BasketItemDto basketItemDto,
                                                                     Pageable pageable) {
        return new ResponseEntity<>(basketService.getAllProductInBasket(userId, basketItemDto, pageable), HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @GetMapping("/{userId}/total")
    public ResponseEntity<TotalElements> getTotalElements(@PathVariable Long userId) {
        return new ResponseEntity<>(basketService.getTotalElements(userId), HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @GetMapping(value = "/{userId}/export/csv", produces = "text/csv")
    public @ResponseBody
    ResponseEntity<Void> getBasketInCsv(@PathVariable Long userId,
                                        HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        fileService.toCsv(userId, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @PostMapping(value = "/{userId}/import/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> setBasketFromCsv(@PathVariable Long userId,
                                                         @RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.setBasketFromCsv(userId, file), HttpStatus.OK);
    }

    @PreAuthorize("#userId == principal.id")
    @GetMapping(value = "/{userId}/export/xlxs", produces = "text/xlsx")
    public @ResponseBody
    ResponseEntity<InputStreamResource> getBasketInXlxs(@PathVariable Long userId, HttpServletResponse response,
                                                        @RequestParam(value = "group", required = false) boolean group) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new InputStreamResource(fileService.toXlxs(userId, response, group)));
    }

    @PreAuthorize("#userId == principal.id")
    @PostMapping(value = "/{userId}/import/xlxs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> setBasketFromXlxs(@PathVariable Long userId,
                                                          @RequestParam("file") MultipartFile file) throws IOException, SpreadsheetReadException, OpenXML4JException {
        return new ResponseEntity<>(fileService.setBasketFromXlxs(userId, file), HttpStatus.OK);
    }
}
