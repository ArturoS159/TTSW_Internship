package pl.com.ttsw.intership.wholesaleserverapp.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.OrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.TotalElements;

public interface OrderService {

    void finishOrder(Long orderId, String warehouseName);

    Page<OrderDto> getAllOrders(Long userId, OrderDto orderDto, Pageable pageable);

    TotalElements getTotalElements(Long userId, boolean active);
}
