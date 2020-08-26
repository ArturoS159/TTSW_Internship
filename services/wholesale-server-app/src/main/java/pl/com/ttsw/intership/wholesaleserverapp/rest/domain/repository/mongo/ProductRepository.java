package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Products;

@Repository
public interface ProductRepository extends MongoRepository<Products, String>, ProductRepositoryCustom {
}