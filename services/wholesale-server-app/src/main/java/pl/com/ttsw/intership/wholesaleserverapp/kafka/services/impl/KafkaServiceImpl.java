package pl.com.ttsw.intership.wholesaleserverapp.kafka.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.services.KafkaService;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.invoice.Invoice;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.orderList.OrderList;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${app.kafka.invoice: nazwa}")
    private String invoiceTopic;

    @Value("${app.kafka.orders: order}")
    private String orderTopic;

    /**
     * Function sends invoice object to the kafka topic
     * @param invoice invoice object
     */
    @Override
    public void sendInvoice(Invoice invoice){
        Message<Invoice> message = MessageBuilder
                .withPayload(invoice)
                .setHeader(KafkaHeaders.TOPIC, invoiceTopic)
                .build();
        kafkaTemplate.send(message);
        log.info("Invoice send!");
    }

    /**
     * Function sends order list object to the kafka topic
     * @param orderList order list object
     */
    @Override
    public void sendOrderList(OrderList orderList){
        Message<OrderList> message = MessageBuilder
                .withPayload(orderList)
                .setHeader(KafkaHeaders.TOPIC, orderTopic)
                .build();
        kafkaTemplate.send(message);
        log.info("Order list send!");
    }

}
