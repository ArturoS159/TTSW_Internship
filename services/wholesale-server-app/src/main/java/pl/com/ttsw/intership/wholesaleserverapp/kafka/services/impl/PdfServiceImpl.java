package pl.com.ttsw.intership.wholesaleserverapp.kafka.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.exception.pdf.PdfException;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.repository.PdfInvoiceRepository;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.repository.PdfOrderRepository;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.services.KafkaService;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.services.PdfService;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.invoice.BasketItemDto;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.invoice.Invoice;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.orderList.OrderList;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.pdf.InvoicePdf;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.pdf.OrderPdf;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.BasketItem;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.OrderRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.OrderNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.UserDoesntHaveAccessException;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.UserNotFoundException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService {

    @NonNull private final PdfInvoiceRepository pdfInvoiceRepository;
    @NonNull private final PdfOrderRepository pdfOrderRepository;
    @NonNull private final KafkaService kafkaService;
    @NonNull private final UserRepository userRepository;
    @NonNull private final OrderRepository orderRepository;

    @Value("${app.pdf.times.requested: 2}")
    private int REQUESTED_TIMES_TO_ASK_DB;
    /**
     * Function check if user is associated with invoice, if not it will return exception with message 'You dont have permission to view!'.
     * Function will check 2 times if there is data in database. It's because jasper service could be slower then our request.
     * @param invoiceNr invoice number
     * @param principal requested user
     * @return pdf in byte
     * @throws InterruptedException
     */
    @Override
    public byte[] decodeInvoice(Long invoiceNr, Principal principal) throws InterruptedException {
        Order order = orderRepository.findByOrderId(invoiceNr).orElseThrow(OrderNotFoundException::new);
        if(!order.getUser().getEmail().equals(principal.getName())) throw new PdfException("You don't have permission to view!");

        int i = 0;
        while(i<REQUESTED_TIMES_TO_ASK_DB){ //this loop will check if jasper service added pdf into database,
            Optional<InvoicePdf> inv = pdfInvoiceRepository.findByInvoiceId(invoiceNr);
            if(inv.isPresent()){
                return inv.get().getPdf();
            }
            Thread.sleep(2000);
            i++;
        }
        throw new PdfException("Pdf file not found!");
    }

    /**
     * Function check if user is associated with order, if not it will return exception with message 'You dont have permission to view!'.
     * Function will check 2 times if there is data in database. It's because jasper service could be slower then our request.
     * @param orderNr
     * @param principal
     * @return
     * @throws InterruptedException
     */
    @Override
    public byte[] decodeOrder(Long orderNr, Principal principal) throws InterruptedException {
        Order order = orderRepository.findByOrderId(orderNr).orElseThrow(OrderNotFoundException::new);
        User worker = userRepository.findByEmail(principal.getName()).orElseThrow(()-> {throw new UserNotFoundException("User not found!");});

        boolean ifWorkerIsOwner = (order.getWarehouse().getUser().getUserId() == worker.getUserId());
        boolean ifWorkerWorksInWarehouse = (worker.getWarehouseWorker() == order.getWarehouse().getWarehouseId());

        if( !ifWorkerIsOwner && !ifWorkerWorksInWarehouse){
            throw new UserDoesntHaveAccessException("You don't have permission to view this data!");
        }

        int i = 0;
        while(i<REQUESTED_TIMES_TO_ASK_DB){
            Optional<OrderPdf> ord = pdfOrderRepository.findByOrderNr(orderNr);
            if(ord.isPresent()){
                return ord.get().getPdf();
            }
            Thread.sleep(2000);
            i++;
        }
        throw new PdfException("Pdf file not found!");
    }

    /**
     * Function get list of orders, and split it into different warehouse.
     * @param orders list of orders
     * @param user user
     */
    @Override
    public void generatePdfFromOrder(List<Order> orders, User user) {
        for (Order order: orders) {
            OrderList orderList = new OrderList();
            List<BasketItemDto> bil = new ArrayList<>();
            //for map items in basket
            for (BasketItem bi : order.getBasketItemList()){
                bil.add(BasketItemDto.buildFromBasketItem(bi));
            }
            orderList.setOrder_nr(order.getOrderId());
            orderList.setBasketItems(bil);
            kafkaService.sendOrderList(orderList);

            kafkaService.sendInvoice(Invoice.buildInvoiceFromOrderAndUser(order, user));
        }

    }


}
