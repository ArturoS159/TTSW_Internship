package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class BasketItemDto {
    private Long basketId;
    private Long orderId;
    private String image;
    private String manufacturer;
    private String warehouse;
    private String name;
    private BigDecimal price;
    private long amount;
    private String search;
    private List<String> manufacturerFilter;
    private List<String> warehouseFilter;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
    private Long userId;
}
