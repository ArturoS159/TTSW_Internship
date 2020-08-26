package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);

    Page<User> findAllByWarehouseWorker(@Param("warehouseWorker") Long warehouseWorker, Pageable pageable);

    boolean existsByEmail(@Param("email") String email);
}
