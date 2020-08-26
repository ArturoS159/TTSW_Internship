package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private String image;
    private String manufacturer;
    private String warehouse;
    private BigDecimal price;
    private long maxAmount;
    private String search;
    private List<String> manufactureFilter;
    private List<String> warehouseFilter;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;

}