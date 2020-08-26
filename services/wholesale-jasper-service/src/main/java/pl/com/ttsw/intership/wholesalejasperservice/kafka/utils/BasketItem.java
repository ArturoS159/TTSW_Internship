package pl.com.ttsw.intership.wholesalejasperservice.kafka.utils;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BasketItem {

    private int lp;
    private String basketUUID;
    //private String email;
    private Date date;
    private String productName;
    private String producer;
    private BigDecimal price;
    private int quantity;
    private String warehouseName;
    //private String address;
    private boolean active;
    private String img;





}
