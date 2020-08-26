package pl.com.ttsw.intership.soapservice.services;


import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.soap_second_warehouse.Producer;
import pl.com.ttsw.intership.soap_second_warehouse.Product;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class SecondWarehouseService {

    List<Product> productsList;

    public SecondWarehouseService() {
        productsList = new ArrayList<>();

        Producer producer1 = new Producer();
        producer1.setName("SecondWarehouseProducerName1");
        producer1.setCity("SecondWarehouseCity1");

        Producer producer2 = new Producer();
        producer2.setName("SecondWarehouseProducerName2");
        producer2.setCity("SecondWarehouseCity2");

        Producer producer3 = new Producer();
        producer3.setName("SecondWarehouseProducerName3");
        producer3.setCity("SecondWarehouseCity3");


        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductCategory("SecondWarehouseCategory1");
        product1.setPhoto("SecondWarehousePhoto1");
        product1.setName("SecondWarehouseName1");
        product1.setProducer(producer1);
        product1.setPrice(BigDecimal.valueOf(32));
        product1.setQuantity(344L);
        product1.setDescription("SecondWarehouseDescription1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProductCategory("SecondWarehouseCategory2");
        product2.setPhoto("SecondWarehousePhoto2");
        product2.setName("SecondWarehouseName2");
        product2.setProducer(producer2);
        product2.setPrice(BigDecimal.valueOf(12));
        product2.setQuantity(631L);
        product1.setDescription("SecondWarehouseDescription2");

        Product product3 = new Product();
        product3.setId(3L);
        product3.setProductCategory("SecondWarehouseCategory3");
        product3.setPhoto("SecondWarehousePhoto3");
        product3.setName("SecondWarehouseName3");
        product3.setProducer(producer3);
        product3.setPrice(BigDecimal.valueOf(66));
        product3.setQuantity(752L);
        product1.setDescription("SecondWarehouseDescription3");

        productsList.add(product1);
        productsList.add(product2);
        productsList.add(product3);

    }

    public List<Product> getProductsList() {
        return productsList;
    }
}
