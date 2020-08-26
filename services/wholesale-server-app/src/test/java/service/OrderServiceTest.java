package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.services.PdfService;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Warehouse;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.OrderRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.UserNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.impl.OrderServiceImpl;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PdfService pdfService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order prepareOrder(String warehouseName){
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseName(warehouseName);
        Order order = new Order();
        order.setWarehouse(warehouse);
        order.setActive(true);
        return order;
    }

    @Test
    void shouldThrowExceptionUserNotFound(){
        //then
        Assertions.assertThrows(UserNotFoundException.class,() -> orderService.finishOrder(1L,"Warehouse"));
    }

    @Test
    void shouldFinishAllUserOrders(){
        //given
        User user = new User();
        Order o1 = prepareOrder("W1");
        Order o2 = prepareOrder("W2");
        Order o3 = prepareOrder("W3");
        user.setOrderList(Arrays.asList(o1,o2,o3));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(orderRepository.findAllByUserAndActive(user,true)).willReturn(Arrays.asList(o1,o2,o3));

        //when
        orderService.finishOrder(1L,null);

        //then
        Assertions.assertFalse(user.getOrderList().get(0).isActive());
        Assertions.assertFalse(user.getOrderList().get(1).isActive());
        Assertions.assertFalse(user.getOrderList().get(2).isActive());
    }

    @Test
    void shouldFinishSpecificUserOrders(){
        //given
        User user = new User();
        Order o1 = prepareOrder("W1");
        Order o2 = prepareOrder("W2");
        Order o3 = prepareOrder("W3");
        user.setOrderList(Arrays.asList(o1,o2,o3));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(orderRepository.findByUserAndWarehouseWarehouseNameAndActive(user, "W1",true)).willReturn(Optional.of(o1));
        given(orderRepository.findByUserAndWarehouseWarehouseNameAndActive(user, "W3", true)).willReturn(Optional.of(o3));

        //when
        orderService.finishOrder(1L,"W1");
        orderService.finishOrder(1L,"W3");

        //then
        Assertions.assertFalse(user.getOrderList().get(0).isActive());
        Assertions.assertTrue(user.getOrderList().get(1).isActive());
        Assertions.assertFalse(user.getOrderList().get(2).isActive());
    }

}
