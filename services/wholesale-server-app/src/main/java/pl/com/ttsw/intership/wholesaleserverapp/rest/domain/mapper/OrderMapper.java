package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper;

import org.mapstruct.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.OrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Product;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Warehouse;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

@Mapper(componentModel = "spring", uses = {BasketItemMapper.class, WarehouseMapper.class})
public interface OrderMapper {

    @Mapping(target = "warehouse", source = "warehouse.warehouseName")
    @Mapping(target = "warehouseId", source = "warehouse.warehouseId")
    @Mapping(target = "warehouseData", source = "warehouse")
    @Mapping(target = "userId", source = "user.userId")
    OrderDto toOrderDto(Order order);

    @Mapping(target = "warehouse", source = "warehouse")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "user.userId", source = "user.userId")
    Order toOrder(Product product, User user, Warehouse warehouse, @Context LocalDate date);

    @AfterMapping
    default Order afterOrderMapping(@MappingTarget Order order, @Context LocalDate date) {
        order.setTotalPrice(BigDecimal.ZERO);
        order.setTotalAmount(0L);
        if (date == null) {
            order.setDateSell(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        } else {
            order.setDateSell(Date.valueOf(date));
        }
        return order;
    }
}
