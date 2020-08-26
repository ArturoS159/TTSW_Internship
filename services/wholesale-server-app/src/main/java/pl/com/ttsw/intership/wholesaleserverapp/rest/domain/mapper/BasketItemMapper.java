package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.BasketItemDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.BasketItem;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BasketItemMapper {

    List<BasketItemDto> toBasketItemListDto(List<BasketItem> basketItemList);

    @Mapping(target = "orderId", source = "basketItem.order.orderId")
    @Mapping(target = "warehouse", source = "order.warehouse.warehouseName")
    BasketItemDto toBasketItemDto(BasketItem basketItem);

    @Mapping(target = "basketId", ignore = true)
    @Mapping(target = "name", source = "product.name")
    BasketItem toBasketItem(Product product, Order order);

}
