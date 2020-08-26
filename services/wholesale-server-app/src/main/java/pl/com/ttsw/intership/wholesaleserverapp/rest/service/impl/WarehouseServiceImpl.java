package pl.com.ttsw.intership.wholesaleserverapp.rest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.OrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UpdateOrderStatusDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.UserDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.WarehouseDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Warehouse;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.OrderStatus;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper.OrderMapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper.UserMapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper.WarehouseMapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.OrderRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.WarehouseRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.WarehouseService;
import pl.com.ttsw.intership.wholesaleserverapp.rest.specification.OrderSpecification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;

import static java.util.Objects.nonNull;

@AllArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseMapper warehouseMapper;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    @Override
    public void addWarehouseForUser(Long userId, Warehouse warehouse) {
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (warehouseRepository.findByWarehouseName(warehouse.getWarehouseName()).isPresent()) {
            throw new WarehouseAlreadyExistException();
        }
        warehouse.setUser(user);
        warehouseRepository.save(warehouse);
    }

    @Override
    public void updateWarehouseData(Long warehouseId, Warehouse warehouse) {
        final Warehouse warehouseInDatabase = warehouseRepository.findById(warehouseId).orElseThrow(WarehouseNotFoundException::new);
        final Warehouse warehouseUpdated = warehouseMapper.updateWarehouse(warehouseInDatabase, warehouse);
        warehouseRepository.save(warehouseUpdated);
    }

    @Override
    public Page<OrderDto> getAllOrdersInWarehouse(Long userId, Long warehouseId, OrderDto orderDto, Pageable pageable) {
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        final Warehouse warehouse = warehouseRepository.findByWarehouseIdAndUserUserId(warehouseId, userId).orElseThrow(WarehouseNotFoundException::new);
        if (warehouse.getWarehouseId().equals(user.getWarehouseWorker()) || nonNull(user.getUserId()) && warehouse.getUser().getUserId().equals(user.getUserId())) {
            orderDto.setWarehouseFilter(Collections.singletonList(warehouse.getWarehouseName()));
            Specification<Order> orderSpecification = new OrderSpecification(orderDto);
            return orderRepository.findAll(orderSpecification, pageable).map(orderMapper::toOrderDto);
        }
        return null;
    }

    @Override
    public void updateOrderStatusInWarehouse(Long warehouseId, Long orderId, UpdateOrderStatusDto updateOrderStatusDto) {
        warehouseRepository.findById(warehouseId).orElseThrow(WarehouseNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.setStatus(updateOrderStatusDto.getOrderStatus());
        if(updateOrderStatusDto.getOrderStatus().equals(OrderStatus.DONE)){
            order.setDateFinalize(Date.valueOf(LocalDate.now()));
        }
        orderRepository.save(order);
    }

    @Override
    public Page<UserDto> getAllWorkersInWarehouse(Long warehouseId, Pageable pageable) {
        return userRepository.findAllByWarehouseWorker(warehouseId, pageable).map(userMapper::toUserDto);
    }

    @Override
    public Page<WarehouseDto> getAllWarehouse(Long userId, Pageable pageable) {
        return warehouseRepository.findAllByUserUserId(userId, pageable).map(warehouseMapper::toWarehouseDto);
    }

    @Override
    public void deleteUserFromWarehouse(Long warehouseId, Long workerId) {
        User worker = userRepository.findById(workerId).orElseThrow(UserNotFoundException::new);
        if(nonNull(worker.getWarehouseWorker())&&worker.getWarehouseWorker().equals(warehouseId)){
            worker.setWarehouseWorker(null);
            userRepository.save(worker);
        }
    }

    @Override
    public void addUserToWarehouse(Long warehouseId, UserDto userDto) {
        User worker = userRepository.findByEmail(userDto.getEmail()).orElseThrow(UserNotFoundException::new);
        final Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(WarehouseNotFoundException::new);
        if(nonNull(worker.getWarehouseWorker())){
            throw new UserAlreadyWorkingInWarehouse();
        }
        worker.setWarehouseWorker(warehouse.getWarehouseId());
        userRepository.save(worker);
    }

    @Override
    public void assignOrderAsMe(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.setAssignedBy(userId);
        orderRepository.save(order);
    }

    @Override
    public void unAssignOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.setAssignedBy(null);
        orderRepository.save(order);
    }
}