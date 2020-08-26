package pl.com.ttsw.intership.wholesalejasperservice.kafka.service.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.wholesalejasperservice.jasper.service.JasperReportService;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.invoice.Invoice;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.orderList.OrderList;

@Service
@Slf4j
@Data
public class KafkaListenerServiceImpl {

    private JasperReportService reportService;

    public KafkaListenerServiceImpl(JasperReportService reportService){
        this.reportService = reportService;
    }

    @KafkaListener(topics = "${app.kafka.orders}",
            containerFactory = "kafkaListenerOrderContainerFactory")
    public void receiveOrder(@Payload OrderList orderList,
                             @Headers MessageHeaders headers){
        log.info("received data='{}'", orderList);

        headers.keySet().forEach(key -> {
            log.info("{}: {}", key, headers.get(key));
        });
       reportService.saveOrderAsPdf(orderList);
    }

    @KafkaListener(topics = "${app.kafka.invoice}",
            containerFactory = "kafkaListenerInvoiceContainerFactory")
    public void receiveInvoice(@Payload Invoice invoice,
                               @Headers MessageHeaders headers){

        log.info("received data='{}'", invoice);

        headers.keySet().forEach(key -> {
            log.info("{}: {}", key, headers.get(key));
        });
        reportService.saveInvoiceAsPdf(invoice);

    }
}
