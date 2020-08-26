package pl.com.ttsw.intership.wholesaleserverapp.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.OrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UpdateOrderStatusDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UserDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.WarehouseDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Warehouse;

public interface WarehouseService {
    void addWarehouseForUser(Long userId, Warehouse warehouse);

    void updateWarehouseData(Long warehouseId, Warehouse warehouse);

    Page<OrderDto> getAllOrdersInWarehouse(Long userId, Long warehouseId, OrderDto orderDto, Pageable pageable);

    void updateOrderStatusInWarehouse(Long warehouseId, Long orderId, UpdateOrderStatusDto updateOrderStatusDto);

    Page<UserDto> getAllWorkersInWarehouse(Long warehouseId, Pageable pageable);

    Page<WarehouseDto> getAllWarehouse(Long warehouseId, Pageable pageable);

    void deleteUserFromWarehouse(Long warehouseId, Long workerId);

    void addUserToWarehouse(Long warehouseId, UserDto userDto);

    void assignOrderAsMe(Long orderId, Long userId);

    void unAssignOrder(Long orderId, Long userId);
}
