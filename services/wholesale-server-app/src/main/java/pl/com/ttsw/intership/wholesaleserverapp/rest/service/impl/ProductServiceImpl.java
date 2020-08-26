package pl.com.ttsw.intership.wholesaleserverapp.rest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.TotalElements;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Product;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.mongo.ProductRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.exception.NoProductInDatabaseException;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.ProductService;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> getAllProducts(Product product, Pageable pageable) {
        return productRepository.findAllProduct(product, pageable);
    }

    @Override
    public TotalElements getTotalElements() {
        TotalElements totalElements = new TotalElements();
        totalElements.setManufacture(productRepository.findAllManufacturer().orElseThrow(NoProductInDatabaseException::new));
        totalElements.setWarehouse(productRepository.findAllWarehouse().orElseThrow(NoProductInDatabaseException::new));
        totalElements.setMinMax(productRepository.findMinAndMaxPrice().orElseThrow(NoProductInDatabaseException::new));
        return totalElements;
    }

}