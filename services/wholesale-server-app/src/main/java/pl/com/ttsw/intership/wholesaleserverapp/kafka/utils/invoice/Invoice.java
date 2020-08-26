package pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.invoice;

import lombok.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.BasketItem;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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


    private List<BasketItemDto> basketItems;

    public static Invoice buildInvoiceFromOrderAndUser(Order o, User user){
        Invoice invoice = new Invoice();
        invoice.setInvoiceNr(o.getOrderId());
        invoice.setDateIssue(new Date());
        invoice.setDateSell(o.getDateSell());
        invoice.setDateFinalize(o.getDateFinalize());
        invoice.setWarehouseName(o.getWarehouse().getWarehouseName());
        invoice.setWarehouseCity(o.getWarehouse().getCity());
        invoice.setWarehouseAddress(o.getWarehouse().getStreet());
        invoice.setBuyerName(user.getName());
        invoice.setBuyerAddress(user.getStreet());
        invoice.setBuyerPostCode(user.getPostCode());
        invoice.setBuyerCity(user.getCity());
        invoice.setBuyerNip(user.getNip());
        invoice.setBuyerPhone(user.getPhoneNumber());
        invoice.setBuyerEmail(user.getEmail());

        invoice.setSellerName(o.getWarehouse().getUser().getName());
        invoice.setSellerAddress(o.getWarehouse().getUser().getStreet());
        invoice.setSellerPostCode(o.getWarehouse().getUser().getPostCode());
        invoice.setSellerCity(o.getWarehouse().getUser().getCity());
        invoice.setSellerNip(o.getWarehouse().getUser().getNip());
        invoice.setSellerPhone(o.getWarehouse().getUser().getPhoneNumber());
        invoice.setSellerEmail(o.getWarehouse().getUser().getEmail());

        List<BasketItemDto> list = new ArrayList<>();
        for (BasketItem basketItem : o.getBasketItemList()) {
            list.add(BasketItemDto.buildFromBasketItem(basketItem));
        }
        invoice.setBasketItems(list);
        return invoice;
    }


}
