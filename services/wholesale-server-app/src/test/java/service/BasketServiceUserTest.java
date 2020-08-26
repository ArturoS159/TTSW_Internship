package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.AddItemToOrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper.BasketItemMapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper.OrderMapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.mongo.ProductRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.BasketItemRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.OrderRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.WarehouseRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.BasketItemNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.OrderNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.ProductNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.impl.BasketServiceImpl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BasketServiceUserTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private BasketItemMapper basketItemMapper;

    @Mock
    private BasketItemRepository basketItemRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private BasketServiceImpl basketService;

    AddItemToOrderDto prepareAddItem(Long amount){
        AddItemToOrderDto addItemToOrderDto = new AddItemToOrderDto();
        addItemToOrderDto.setWarehouse("Warehouse");
        addItemToOrderDto.setProductId("1");
        addItemToOrderDto.setAmount(amount);
        return addItemToOrderDto;
    }

    Product prepareProduct(BigDecimal price, String name){
        Product product = new Product();
        product.setWarehouse("Warehouse");
        product.setPrice(price);
        product.setName(name);
        return product;
    }

    Order prepareOrder(Long amount,BigDecimal totalPrice){
        Order order = new Order();
        order.setOrderId(1L);
        order.setTotalAmount(amount);
        order.setTotalPrice(totalPrice);
        return order;
    }

    BasketItem prepareBasketItem(Long amount,BigDecimal price){
        BasketItem basketItem = new BasketItem();
        basketItem.setBasketId(1L);
        basketItem.setAmount(amount);
        basketItem.setPrice(price);
        return basketItem;
    }

    @Test
    void shouldThrowExceptionProductNotFound(){
        //given
        AddItemToOrderDto addItemToOrderDto = prepareAddItem(1L);

        //then
        Assertions.assertThrows(ProductNotFoundException.class,() -> basketService.addBasketToOrder(addItemToOrderDto,1L, null));
    }

    @Test
    void shouldAddBasketItemToOrderAndCountPrice(){
        //given
        Product product = prepareProduct(BigDecimal.valueOf(12),"ProductName");
        AddItemToOrderDto addItemToOrderDto = prepareAddItem(4L);
        User user = new User();
        Warehouse warehouse = new Warehouse();
        Order order = prepareOrder(12L,BigDecimal.ZERO);
        BasketItem basketItem = prepareBasketItem(3L,BigDecimal.TEN);

        given(productRepository.findProductByWarehouseAndIdOrName(addItemToOrderDto.getWarehouse(),addItemToOrderDto.getProductId(),null)).willReturn(Optional.of(product));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(warehouseRepository.findByWarehouseName(addItemToOrderDto.getWarehouse())).willReturn(Optional.of(warehouse));
        given(orderRepository.findByUserAndWarehouseWarehouseNameAndActive(user, warehouse.getWarehouseName(),true)).willReturn(Optional.ofNullable(order));
        given(basketItemMapper.toBasketItem(product, order)).willReturn(basketItem);

        //when
        basketService.addBasketToOrder(addItemToOrderDto,1L,null);

        //then
        Assertions.assertEquals(BigDecimal.valueOf(48),order.getTotalPrice());
        Assertions.assertEquals(16,order.getTotalAmount());
    }

    @Test
    void shouldAddAmountToExistingBasketItemInOrder(){
        //given
        Product product1 = prepareProduct(BigDecimal.valueOf(31),"ProductName");
        Product product2 = prepareProduct(BigDecimal.valueOf(31),"ProductName");
        AddItemToOrderDto addItemToOrderDto = prepareAddItem(4L);
        User user = new User();
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseName("Warehouse");
        Order order = prepareOrder(12L,BigDecimal.ZERO);

        BasketItem basketItem = prepareBasketItem(3L,BigDecimal.TEN);
        given(productRepository.findProductByWarehouseAndIdOrName(addItemToOrderDto.getWarehouse(),addItemToOrderDto.getProductId(),null)).willReturn(Optional.of(product1));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(warehouseRepository.findByWarehouseName(addItemToOrderDto.getWarehouse())).willReturn(Optional.of(warehouse));
        given(orderRepository.findByUserAndWarehouseWarehouseNameAndActive(user, warehouse.getWarehouseName(),true)).willReturn(Optional.ofNullable(order));
        given(basketItemRepository.findByNameAndWarehouseAndUser(product2.getName(), product2.getWarehouse(), user)).willReturn(Optional.ofNullable(basketItem));

        //when
        basketService.addBasketToOrder(addItemToOrderDto,1L,null);

        //then
        Assertions.assertEquals(BigDecimal.valueOf(124),order.getTotalPrice());
        Assertions.assertEquals(16,order.getTotalAmount());
        Assertions.assertEquals(7,basketItem.getAmount());
    }

    @Test
    void shouldThrowOrderNotFound(){
        //then
        Assertions.assertThrows(OrderNotFoundException.class,() -> basketService.delBasketItemFromOrder(1L,1L));
    }

    @Test
    void shouldThrowBasketItemNotFound(){
        //given
        Order order = new Order();
        given(orderRepository.findByOrderIdAndActive(anyLong(),anyBoolean())).willReturn(Optional.of(order));

        //then
        Assertions.assertThrows(BasketItemNotFoundException.class,() -> basketService.delBasketItemFromOrder(1L,1L));
    }

    @Test
    void shouldCountItemAmountAndPrice(){
        //given
        Order order1 = prepareOrder(12L,BigDecimal.ZERO);
        BasketItem basketItem1 = prepareBasketItem(3L,BigDecimal.valueOf(32));
        BigDecimal price = order1.getTotalPrice().subtract(basketItem1.getPrice().multiply(BigDecimal.valueOf(basketItem1.getAmount())));

        given(orderRepository.findByOrderIdAndActive(1L, true)).willReturn(Optional.of(order1));
        given(basketItemRepository.findByBasketIdAndOrder(1L, order1)).willReturn(Optional.of(basketItem1));
        given(orderRepository.save(order1)).willReturn(order1);

        //when
        basketService.delBasketItemFromOrder(order1.getOrderId(),basketItem1.getBasketId());

        //then
        Assertions.assertEquals(price,order1.getTotalPrice());
        Assertions.assertEquals(9L,order1.getTotalAmount());
    }

    @Test
    void shouldUpdateBasketItemInOrder(){
        //given
        BasketItem basketItem1 = prepareBasketItem(5L,BigDecimal.TEN);
        Order order = prepareOrder(5L,BigDecimal.valueOf(50));
        order.setBasketItemList(Collections.singletonList(basketItem1));
        basketItem1.setOrder(order);
        given(basketItemRepository.findById(1L)).willReturn(Optional.of(basketItem1));

        //when
        basketService.updateBasketItem(1L,2L);

        //then
        Assertions.assertEquals(2,basketItem1.getAmount());
        Assertions.assertEquals(2,order.getTotalAmount());
        Assertions.assertEquals(BigDecimal.valueOf(20),order.getTotalPrice());
    }

    @Test
    void shouldNotUpdateBasketItemInOrder(){
        //given
        BasketItem basketItem1 = prepareBasketItem(5L,BigDecimal.TEN);
        Order order = prepareOrder(5L,BigDecimal.valueOf(50));
        order.setBasketItemList(Collections.singletonList(basketItem1));
        basketItem1.setOrder(order);
        given(basketItemRepository.findById(1L)).willReturn(Optional.of(basketItem1));

        //when
        basketService.updateBasketItem(1L,-2L);
        basketService.updateBasketItem(1L,5L);
        basketService.updateBasketItem(1L,0L);

        //then
        Assertions.assertEquals(5,basketItem1.getAmount());
        Assertions.assertEquals(5,order.getTotalAmount());
        Assertions.assertEquals(BigDecimal.valueOf(50),order.getTotalPrice());
    }
}