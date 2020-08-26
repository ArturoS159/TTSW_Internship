package pl.com.ttsw.intership.wholesalejasperservice.kafka.service;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.invoice.Invoice;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.orderList.OrderList;



public interface KafkaListenerService {

     void receiveOrder(@Payload OrderList orderList, @Headers MessageHeaders headers);

     void receiveInvoice(@Payload Invoice invoice, @Headers MessageHeaders headers);
}
