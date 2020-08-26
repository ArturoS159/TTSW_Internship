package pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.orderList;

import lombok.Data;
import pl.com.ttsw.intership.wholesalejasperservice.kafka.utils.BasketItem;

import java.util.List;

@Data
public class OrderList {

    private long order_nr;
    private List<BasketItem> basketItems;
}
