package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto;

import lombok.Getter;
import lombok.Setter;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.OrderStatus;

@Setter
@Getter
public class UpdateOrderStatusDto {
    private OrderStatus orderStatus;
}
