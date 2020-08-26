package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Warehouse;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByWarehouseName(@Param("warehouseName") String warehouseName);

    Optional<Warehouse> findByWarehouseIdAndUserUserId(@Param("warehouseId") Long warehouseId, @Param("userId") Long userId);

    Page<Warehouse> findAllByUserUserId(@Param("userId") Long userId, Pageable pageable);
}
