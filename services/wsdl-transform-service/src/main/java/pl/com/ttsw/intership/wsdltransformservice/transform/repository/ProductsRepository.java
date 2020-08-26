package pl.com.ttsw.intership.wsdltransformservice.transform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.product_model.Products;

@Repository
public interface ProductsRepository extends MongoRepository<Products, String> {
}
