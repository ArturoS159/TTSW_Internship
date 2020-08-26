package pl.com.ttsw.intership.wholesaleserverapp.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.TotalElements;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Product;

public interface ProductService {
    Page<Product> getAllProducts(Product product, Pageable pageable);

    TotalElements getTotalElements();
}
