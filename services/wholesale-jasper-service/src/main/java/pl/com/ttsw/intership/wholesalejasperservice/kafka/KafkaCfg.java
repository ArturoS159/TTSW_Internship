package pl.com.ttsw.intership.wholesalejasperservice.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.invoice.Invoice;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.orderList.OrderList;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaCfg {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "1088583");

        //Trusted package
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return props;
    }

    //order
    @Bean
    public ConsumerFactory<String, OrderList> consumerOrderFactory(){


        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer2(new JsonDeserializer<>(OrderList.class, false)) //This line fixed error loop issue
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderList> kafkaListenerOrderContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, OrderList> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerOrderFactory());
        return factory;
    }

    //invoice
    @Bean
    public ConsumerFactory<String, Invoice> consumerInvoiceFactory(){


        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer2(new JsonDeserializer<>(Invoice.class, false)) //This line fixed error loop issue
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Invoice> kafkaListenerInvoiceContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Invoice> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerInvoiceFactory());
        return factory;
    }


}
