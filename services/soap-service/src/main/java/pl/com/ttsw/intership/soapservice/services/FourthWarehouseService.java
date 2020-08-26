package pl.com.ttsw.intership.soapservice.services;

import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.soap_fourth_warehouse.Producer;
import pl.com.ttsw.intership.soap_fourth_warehouse.Product;
import pl.com.ttsw.intership.soap_fourth_warehouse.ProductData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FourthWarehouseService {

    List<Product> productsList;

    public FourthWarehouseService() {
        productsList = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId(1L);

        ProductData productData1 = new ProductData();
        productData1.setColor("Red");
        productData1.setImg("img");
        productData1.setName("T-shirt");
        productData1.setPrice(BigDecimal.valueOf(23));
        productData1.setQuantity(9);
        productData1.setSize("XL");

        Producer producer1 = new Producer();
        producer1.setCity("Rzeszów");
        producer1.setName("Antioni Rzepiela");
        producer1.setPostCode("33-100");

        product1.setProductData(productData1);
        product1.setProducer(producer1);

        Product product2 = new Product();
        product2.setProductId(2L);

        ProductData productData2 = new ProductData();
        productData2.setColor("Blue");
        productData2.setImg("img");
        productData2.setName("Boots");
        productData2.setPrice(BigDecimal.valueOf(3));
        productData2.setQuantity(3);
        productData2.setSize("35");

        Producer producer2 = new Producer();
        producer2.setCity("Kraków");
        producer2.setName("Zbigniew Grabie");
        producer2.setPostCode("11-111");

        product2.setProductData(productData2);
        product2.setProducer(producer2);

        Product product3 = new Product();
        product3.setProductId(3L);

        ProductData productData3 = new ProductData();
        productData3.setColor("White");
        productData3.setImg("img");
        productData3.setName("Backpack");
        productData3.setPrice(BigDecimal.valueOf(52));
        productData3.setQuantity(5);
        productData3.setSize("NORMAL");

        Producer producer3 = new Producer();
        producer3.setCity("Warszawa");
        producer3.setName("Zbigniew Kołatko");
        producer3.setPostCode("12-345");

        product3.setProductData(productData3);
        product3.setProducer(producer3);

        productsList.add(product1);
        productsList.add(product2);
        productsList.add(product3);
    }

    public List<Product> getAllProducts() {
        return productsList;
    }
}
