package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto;

import lombok.Getter;
import lombok.Setter;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class TotalElements {
    private Long totalAmount;
    private Long totalPrice;
    private List<String> warehouseList;
    private List<String> manufactureList;
    private List<BigDecimal> minMaxList;
    private Set<OrderStatus> statusList;
    private String warehouse;
    private String manufacture;
    private String minMax;
}
