package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class OrderDto {
    private Long orderId;
    private Long userId;
    private Long warehouseId;
    private String warehouse;
    private Date dateSell;
    private Date dateFinalize;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private Long totalAmount;
    private List<BasketItemDto> basketItemList;
    private List<String> warehouseFilter;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDateSell;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDateSell;
    private List<OrderStatus> orderStatusFilter;
    private boolean active;
    private WarehouseDto warehouseData;
    private Long assignedBy;
}


