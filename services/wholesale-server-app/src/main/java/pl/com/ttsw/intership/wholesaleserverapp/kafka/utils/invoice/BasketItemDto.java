package pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.invoice;

import lombok.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.BasketItem;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemDto {

    private int lp;
    private String basketUUID;
    //private String email;
    private Date date;
    private String img;
    private String productName;
    private String producer;
    private BigDecimal price;
    private int quantity;
    private String warehouseName;
    //private String address;
    private boolean active;

    public static BasketItemDto buildFromBasketItem(BasketItem bi){
        BasketItemDto dto = new BasketItemDto();
        dto.setBasketUUID(bi.getBasketId().toString());
        dto.setDate(new Date());
        dto.setImg(bi.getImage());
        dto.setProductName(bi.getName());
        dto.setProducer(bi.getManufacturer());
        dto.setPrice(bi.getPrice());
        dto.setQuantity((int)bi.getAmount());
        dto.setWarehouseName(bi.getOrder().getWarehouse().getWarehouseName());
        return dto;
    }





}
