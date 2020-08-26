package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.BasketItem;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Order;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long>, JpaSpecificationExecutor<BasketItem> {
    Optional<BasketItem> findByBasketIdAndOrder(@Param("basketItemId") Long basketItemId, @Param("order") Order order);

    @Query("SELECT b FROM BasketItem b WHERE b.name = :name AND b.order.warehouse.warehouseName = :warehouse AND b.order.user = :user AND b.order.active=true")
    Optional<BasketItem> findByNameAndWarehouseAndUser(@Param("name") String name, @Param("warehouse") String warehouse, @Param("user") User user);

    @Query("SELECT DISTINCT b.order.warehouse.warehouseName FROM BasketItem b WHERE b.order.user.userId = :userId AND b.order.active=true")
    List<String> findAllWarehouseByUser(@Param("userId") Long userId);

    @Query("SELECT DISTINCT b.manufacturer FROM BasketItem b WHERE b.order.user.userId = :userId AND b.order.active=true")
    List<String> findAllManufactureByUser(@Param("userId") Long userId);

    @Query("SELECT MAX(b.price) FROM BasketItem b WHERE b.order.user.userId = :userId AND b.order.active=true")
    Optional<BigDecimal> findMaxPrice(@Param("userId") Long userId);

    @Query("SELECT MIN(b.price) FROM BasketItem b WHERE b.order.user.userId = :userId AND b.order.active=true")
    Optional<BigDecimal> findMinPrice(@Param("userId") Long userId);

    @Query("SELECT SUM(b.amount) FROM BasketItem b WHERE b.order.user.userId = :userId AND b.order.active=true")
    Long findSumAmount(@Param("userId") Long userId);

    @Query("SELECT SUM(b.amount*b.price) FROM BasketItem b WHERE b.order.user.userId = :userId AND b.order.active=true")
    Long findTotalPrice(@Param("userId") Long userId);

    List<BasketItem> findAllByOrderUserUserIdAndOrderActive(@Param("userId") Long userId, @Param("active") boolean active, Sort sort);
}
