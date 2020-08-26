package pl.com.ttsw.intership.wholesaleserverapp.rest.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.BasketItemDto;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class BasketItemSpecification implements Specification<BasketItem> {
    private final List<String> warehouseFilter;
    private final List<String> manufacturerFilter;
    private final String search;
    private final BigDecimal fromPrice;
    private final BigDecimal toPrice;
    private final Long userId;

    public BasketItemSpecification(BasketItemDto basketItemDto) {
        this.warehouseFilter = basketItemDto.getWarehouseFilter();
        this.manufacturerFilter = basketItemDto.getManufacturerFilter();
        this.search = basketItemDto.getSearch();
        this.fromPrice = basketItemDto.getFromPrice();
        this.toPrice = basketItemDto.getToPrice();
        this.userId = basketItemDto.getUserId();
    }

    @Override
    public Predicate toPredicate(Root<BasketItem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!CollectionUtils.isEmpty(warehouseFilter)) {
            Predicate filterByWarehouse = root.get(BasketItem_.order).get(Order_.warehouse).get(Warehouse_.warehouseName).in(warehouseFilter);
            predicates.add(filterByWarehouse);
        }
        if (!CollectionUtils.isEmpty(manufacturerFilter)) {
            Predicate filterByManufacture = root.get(BasketItem_.MANUFACTURER).in(manufacturerFilter);
            predicates.add(filterByManufacture);
        }
        if (nonNull(search)) {
            Predicate filterBySearch = criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(BasketItem_.NAME)), "%" + search.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(BasketItem_.MANUFACTURER)), "%" + search.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(BasketItem_.order).get(Order_.warehouse).get(Warehouse_.WAREHOUSE_NAME)), "%" + search.toLowerCase() + "%")
            );
            predicates.add(filterBySearch);
        }
        if (nonNull(fromPrice)) {
            Predicate filterByFromPrice = criteriaBuilder.greaterThanOrEqualTo(root.get(BasketItem_.price), fromPrice);
            predicates.add(filterByFromPrice);
        }
        if (nonNull(toPrice)) {
            Predicate filterByToPrice = criteriaBuilder.lessThanOrEqualTo(root.get(BasketItem_.price), toPrice);
            predicates.add(filterByToPrice);
        }
        Predicate filterByUserId = criteriaBuilder.equal(root.get(BasketItem_.order).get(Order_.user).get(User_.USER_ID), userId);
        predicates.add(filterByUserId);
        Predicate filterActive = criteriaBuilder.equal(root.get(BasketItem_.order).get(Order_.active), true);
        predicates.add(filterActive);
        Predicate[] predicatesArray = new Predicate[predicates.size()];
        Predicate[] finalPredicates = predicates.toArray(predicatesArray);
        return criteriaBuilder.and(finalPredicates);
    }

}
