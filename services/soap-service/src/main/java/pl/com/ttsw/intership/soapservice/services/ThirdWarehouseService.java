package pl.com.ttsw.intership.soapservice.services;


import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.soap_third_warehouse.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdWarehouseService {

    List<Product> productsList;

    public ThirdWarehouseService() {
        productsList = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductCategory("ThirdWarehouseCategory1");
        product1.setProductPhoto("ThirdWarehousePhoto1");
        product1.setProductName("ThirdWarehouseName1");
        product1.setProductPrice(BigDecimal.valueOf(44));
        product1.setProductDescription("ThirdWarehouseDescription1");
        product1.setProductProducer("ThirdWarehouse1");
        product1.setProductQuantity(123L);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProductCategory("ThirdWarehouseCategory2");
        product2.setProductPhoto("ThirdWarehousePhoto2");
        product2.setProductName("ThirdWarehouseName2");
        product2.setProductPrice(BigDecimal.valueOf(11));
        product2.setProductDescription("ThirdWarehouseDescription2");
        product2.setProductProducer("ThirdWarehouse2");
        product2.setProductQuantity(442L);

        Product product3 = new Product();
        product3.setId(3L);
        product3.setProductCategory("ThirdWarehouseCategory3");
        product3.setProductPhoto("ThirdWarehousePhoto3");
        product3.setProductName("ThirdWarehouseName3");
        product3.setProductPrice(BigDecimal.valueOf(2));
        product3.setProductDescription("ThirdWarehouseDescription3");
        product3.setProductProducer("ThirdWarehouse3");
        product3.setProductQuantity(2213L);

        productsList.add(product1);
        productsList.add(product2);
        productsList.add(product3);

    }

    public List<Product> getProductsList() {
        return productsList;
    }
}
