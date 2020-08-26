package pl.com.ttsw.intership.soapservice.services;


import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.soap_first_warehouse.Ware;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class FirstWarehouseService {

    List<Ware> waresList;

    public FirstWarehouseService() {
        waresList = new ArrayList<>();

        Ware ware1 = new Ware();
        ware1.setId(1L);
        ware1.setCategory("FirstWarehouseCategory1");
        ware1.setPhoto("FirstWarehousePhoto1");
        ware1.setName("FirstWarehouseName1");
        ware1.setProducer("FirstWarehouseProducer1");
        ware1.setPrice(BigDecimal.valueOf(32));
        ware1.setQuantity(344L);

        Ware ware2 = new Ware();
        ware2.setId(2L);
        ware2.setCategory("FirstWarehouseCategory2");
        ware2.setPhoto("FirstWarehousePhoto2");
        ware2.setName("FirstWarehouseName2");
        ware2.setProducer("FirstWarehouseProducer2");
        ware2.setPrice(BigDecimal.valueOf(12));
        ware2.setQuantity(631L);

        Ware ware3 = new Ware();
        ware3.setId(3L);
        ware3.setCategory("FirstWarehouseCategory3");
        ware3.setPhoto("FirstWarehousePhoto3");
        ware3.setName("FirstWarehouseName3");
        ware3.setProducer("FirstWarehouseProducer3");
        ware3.setPrice(BigDecimal.valueOf(66));
        ware3.setQuantity(752L);

        waresList.add(ware1);
        waresList.add(ware2);
        waresList.add(ware3);

    }

    public List<Ware> getWaresList() {
        return waresList;
    }
}
