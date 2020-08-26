package pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.invoice;

import lombok.*;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.BasketItem;

import java.util.Date;
import java.util.List;

@Data
public class Invoice {

    private long invoiceNr;

    private Date dateIssue;
    private Date dateSell;
    private Date dateFinalize;

    private String warehouseName;
    private String warehouseCity;
    private String warehouseAddress;

    private String buyerName;
    private String buyerAddress;
    private String buyerPostCode;
    private String buyerCity;
    private String buyerNip;
    private String buyerPhone;
    private String buyerEmail;

    private String sellerName;
    private String sellerAddress;
    private String sellerPostCode;
    private String sellerCity;
    private String sellerNip;
    private String sellerPhone;
    private String sellerEmail;


    private List<BasketItem> basketItems;


}
