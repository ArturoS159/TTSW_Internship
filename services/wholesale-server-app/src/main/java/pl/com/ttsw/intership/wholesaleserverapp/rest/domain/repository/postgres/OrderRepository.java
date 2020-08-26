package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByUserAndWarehouseWarehouseNameAndActive(@Param("user") User user, @Param("warehouse") String warehouse, @Param("active") boolean active);

    Optional<Order> findByOrderIdAndActive(@Param("orderId") Long orderId, @Param("active") boolean active);

    Optional<Order> findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT DISTINCT o.warehouse.warehouseName FROM Order o WHERE o.user.userId = :userId AND o.active=:active")
    List<String> findAllWarehouseWarehouseNameByUserUserIdAndActive(@Param("userId") Long userId, @Param("active") boolean active);

    @Query("SELECT DISTINCT o.status FROM Order o WHERE o.user.userId = :userId AND o.active=:active")
    Set<OrderStatus> findDistinctStatusByUserUserIdAndActive(@Param("userId") Long userId, @Param("active") boolean active);

    @Query("SELECT MIN(o.totalPrice) FROM Order o WHERE o.user.userId = :userId AND o.active=:active")
    Optional<BigDecimal> findMinTotalPriceByUserUserIdAndActive(@Param("userId") Long userId, @Param("active") boolean active);

    @Query("SELECT MAX(o.totalPrice) FROM Order o WHERE o.user.userId = :userId AND o.active=:active")
    Optional<BigDecimal> findMaxTotalPriceByUserUserIdAndActive(@Param("userId") Long userId, @Param("active") boolean active);

    List<Order> findAllByUserAndActive(@Param("user") User user, @Param("active") boolean active);
}
