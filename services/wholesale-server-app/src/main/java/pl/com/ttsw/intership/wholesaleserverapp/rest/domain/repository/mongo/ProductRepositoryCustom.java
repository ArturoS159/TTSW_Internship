package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepositoryCustom {
    Page<Product> findAllProduct(Product product, Pageable pageable);

    Optional<Product> findProductByWarehouseAndIdOrName(String warehouse, String productId, String name);

    Optional<String> findAllManufacturer();

    Optional<String> findAllWarehouse();

    Optional<String> findMinAndMaxPrice();
}
