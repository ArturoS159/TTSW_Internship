package pl.com.ttsw.intership.wholesaleserverapp.kafka.services;

import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.invoice.Invoice;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.orderList.OrderList;

public interface KafkaService {

     void sendInvoice(Invoice invoice);

     void sendOrderList(OrderList orderList);

}
