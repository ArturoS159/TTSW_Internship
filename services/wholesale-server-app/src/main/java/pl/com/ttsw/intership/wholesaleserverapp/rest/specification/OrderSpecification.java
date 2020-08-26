package pl.com.ttsw.intership.wholesaleserverapp.rest.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.OrderDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order_;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User_;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Warehouse_;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.OrderStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class OrderSpecification implements Specification<Order> {
    private final String warehouse;
    private final List<String> warehouseFilter;
    private final BigDecimal fromPrice;
    private final BigDecimal toPrice;
    private final LocalDate fromDateSell;
    private final LocalDate toDateSell;
    private final List<OrderStatus> orderStatusFilter;
    private final Long userId;
    private final boolean active;

    public OrderSpecification(OrderDto orderDto) {
        this.warehouse = orderDto.getWarehouse();
        this.warehouseFilter = orderDto.getWarehouseFilter();
        this.fromPrice = orderDto.getFromPrice();
        this.toPrice = orderDto.getToPrice();
        this.fromDateSell = orderDto.getFromDateSell();
        this.toDateSell = orderDto.getToDateSell();
        this.active = orderDto.isActive();
        this.userId = orderDto.getUserId();
        this.orderStatusFilter = orderDto.getOrderStatusFilter();
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!CollectionUtils.isEmpty(warehouseFilter)) {
            Predicate filterByWarehouseFiler = root.get(Order_.warehouse).get(Warehouse_.warehouseName).in(warehouseFilter);
            predicates.add(filterByWarehouseFiler);
        }
        if (nonNull(userId)) {
            Predicate filterByUserId = criteriaBuilder.equal(root.get(Order_.user).get(User_.USER_ID), userId);
            predicates.add(filterByUserId);
        }
        if (nonNull(warehouse)) {
            Predicate filterByWarehouse = criteriaBuilder.like(criteriaBuilder.lower(root.get(Order_.warehouse).get(Warehouse_.WAREHOUSE_NAME)), "%" + warehouse.toLowerCase() + "%");
            predicates.add(filterByWarehouse);
        }
        if (nonNull(fromPrice)) {
            Predicate filterByPrice = criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.totalPrice), fromPrice);
            predicates.add(filterByPrice);
        }
        if (nonNull(toPrice)) {
            Predicate filterByMinPrice = criteriaBuilder.lessThanOrEqualTo(root.get(Order_.totalPrice), toPrice);
            predicates.add(filterByMinPrice);
        }
        if (nonNull(fromDateSell)) {
            Predicate filterByMinDate = criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.dateSell), Date.valueOf(fromDateSell));
            predicates.add(filterByMinDate);
        }
        if (nonNull(toDateSell)) {
            Predicate filterByMaxDate = criteriaBuilder.lessThanOrEqualTo(root.get(Order_.dateSell), Date.valueOf(toDateSell));
            predicates.add(filterByMaxDate);
        }
        if (!CollectionUtils.isEmpty(orderStatusFilter)) {
            Predicate filterByStatus = root.get(Order_.status).in(orderStatusFilter);
            predicates.add(filterByStatus);
        }
        Predicate filterByActive = criteriaBuilder.equal(root.get(Order_.active), active);
        predicates.add(filterByActive);

        Predicate[] predicatesArray = new Predicate[predicates.size()];
        Predicate[] finalPredicates = predicates.toArray(predicatesArray);
        return criteriaBuilder.and(finalPredicates);
    }
}
