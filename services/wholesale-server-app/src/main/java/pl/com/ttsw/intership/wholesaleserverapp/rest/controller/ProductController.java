package pl.com.ttsw.intership.wholesaleserverapp.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.dto.TotalElements;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Product;
import pl.com.ttsw.intership.wholesaleserverapp.rest.service.ProductService;

@RestController
@RequestMapping("/rest-service/product")
@CrossOrigin("*")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getAllProducts(@ModelAttribute("product") Product product,
                                                        Pageable pageable) {
        return new ResponseEntity<>(productService.getAllProducts(product, pageable), HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<TotalElements> getTotalElements() {
        return new ResponseEntity<>(productService.getTotalElements(), HttpStatus.OK);
    }
}
