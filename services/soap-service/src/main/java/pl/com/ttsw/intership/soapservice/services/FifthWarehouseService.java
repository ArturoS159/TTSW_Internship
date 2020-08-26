package pl.com.ttsw.intership.soapservice.services;

import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.soap_fifth_warehouse.Category;
import pl.com.ttsw.intership.soap_fifth_warehouse.Producer;
import pl.com.ttsw.intership.soap_fifth_warehouse.WarehouseProduct;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FifthWarehouseService {

    List<WarehouseProduct> list;

    public FifthWarehouseService() {
        Producer p = new Producer();
        p.setProducerName("name");
        p.setProducerAddress("Address");

        Category c = new Category();
        c.setCategoryName("category name");
        c.setCategoryDescription("desc");

        WarehouseProduct wp = new WarehouseProduct();
        wp.setId(1L);
        wp.setName("product name");
        wp.setCategory(c);
        wp.setPhoto("base64");
        wp.setProducer(p);
        wp.setPrice(BigDecimal.valueOf(12.50));
        wp.setQuantity(100L);
        list = List.of(wp);

    }

    public List<WarehouseProduct> getList() {
        return list;
    }
}

