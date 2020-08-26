package pl.com.ttsw.intership.wholesaleserverapp.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.AddItemToOrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.BasketItemDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.TotalElements;

import java.time.LocalDate;

public interface BasketService {
    void addBasketToOrder(AddItemToOrderDto addItemToOrderDto, Long userId, LocalDate data);

    void delBasketItemFromOrder(Long orderId, Long basketItemId);

    void updateBasketItem(Long basketItemId, Long amount);

    Page<BasketItemDto> getAllProductInBasket(Long userId, BasketItemDto basketItemDto, Pageable pageable);

    TotalElements getTotalElements(Long userId);
}
