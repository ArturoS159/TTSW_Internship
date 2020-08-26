package pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.orderList;

import lombok.Data;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.utils.invoice.BasketItemDto;

import java.util.List;

@Data
public class OrderList {

    private long order_nr;
    private List<BasketItemDto> basketItems;
}
