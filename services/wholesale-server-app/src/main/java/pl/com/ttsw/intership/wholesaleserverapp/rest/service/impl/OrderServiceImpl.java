package pl.com.ttsw.intership.wholesaleserverapp.rest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.wholesaleserverapp.kafka.services.PdfService;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.OrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.TotalElements;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.OrderStatus;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.mapper.OrderMapper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.OrderRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.UserNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.WarehouseNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.OrderService;
import pl.com.ttsw.intership.wholesaleserverapp.rest.specification.OrderSpecification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PdfService pdfService;

    @Override
    public void finishOrder(Long userId, String warehouseName) {
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Order> orderList = new ArrayList<>();
        if (nonNull(warehouseName)) {
            orderList.add(orderRepository.findByUserAndWarehouseWarehouseNameAndActive(user, warehouseName, true).orElseThrow(WarehouseNotFoundException::new));
        } else {
            orderList = orderRepository.findAllByUserAndActive(user, true);
        }
        orderList.forEach(order -> {
            order.setActive(false);
            order.setStatus(OrderStatus.WAITING);
        });
        orderRepository.saveAll(orderList);
        pdfService.generatePdfFromOrder(orderList, user);
    }

    @Override
    public Page<OrderDto> getAllOrders(Long userId, OrderDto orderDto, Pageable pageable) {
        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        orderDto.setUserId(user.getUserId());
        final Specification<Order> specification = new OrderSpecification(orderDto);
        return orderRepository.findAll(specification, pageable).map(orderMapper::toOrderDto);
    }

    @Override
    public TotalElements getTotalElements(Long userId, boolean active) {
        TotalElements totalElements = new TotalElements();
        totalElements.setWarehouseList(orderRepository.findAllWarehouseWarehouseNameByUserUserIdAndActive(userId, active));
        totalElements.setMinMaxList(Arrays.asList(
                orderRepository.findMinTotalPriceByUserUserIdAndActive(userId, active).orElse(BigDecimal.ZERO),
                orderRepository.findMaxTotalPriceByUserUserIdAndActive(userId, active).orElse(BigDecimal.ZERO)
        ));
        totalElements.setStatusList(orderRepository.findDistinctStatusByUserUserIdAndActive(userId, active));
        return totalElements;
    }

}