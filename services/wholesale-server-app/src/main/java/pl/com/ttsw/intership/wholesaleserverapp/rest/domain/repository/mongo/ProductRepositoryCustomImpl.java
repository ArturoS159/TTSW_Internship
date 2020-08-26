package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.mongo;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Product;
import pl.com.ttsw.intership.wholesaleserverapp.rest.pageable.PageHelper;
import pl.com.ttsw.intership.wholesaleserverapp.rest.specification.ProductSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@AllArgsConstructor
@Component
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final MongoTemplate mongoTemplate;
    private final ProductSpecification productSpecification;

    @Override
    public Page<Product> findAllProduct(Product product, Pageable pageable) {

        List<AggregationOperation> aggregationOperationList = productSpecification.toPredicate(product);

        if (pageable.getSort().isSorted()) {
            aggregationOperationList.add(Aggregation.sort(pageable.getSort()));
        }
        final Aggregation aggregation = Aggregation.newAggregation(aggregationOperationList);
        final List<Product> productList = mongoTemplate.aggregate(aggregation, "products", Product.class).getMappedResults();
        return (Page<Product>) PageHelper.preparePage(productList, pageable);
    }

    @Override
    public Optional<Product> findProductByWarehouseAndIdOrName(String warehouse, String productId, String name) {
        List<AggregationOperation> aggregationOperationList = new ArrayList<>();
        aggregationOperationList.add(Aggregation.unwind("product"));
        if (nonNull(productId)) {
            aggregationOperationList.add(Aggregation.match(Criteria.where("product.warehouse").is(warehouse).and("product._id").is(productId)));
        } else {
            aggregationOperationList.add(Aggregation.match(Criteria.where("product.warehouse").is(warehouse).and("product.name").is(name)));
        }
        aggregationOperationList.add(Aggregation.project("product._id", "product.name", "product.image", "product.manufacturer", "product.warehouse", "product.price", "product.amount"));
        aggregationOperationList.add(limit(1));
        Product product;
        final Aggregation aggregation = Aggregation.newAggregation(aggregationOperationList);
        try {
            product = mongoTemplate.aggregate(aggregation, "products", Product.class).getMappedResults().get(0);
        } catch (IndexOutOfBoundsException err) {
            return Optional.empty();
        }
        return Optional.of(product);
    }

    @Override
    public Optional<String> findAllManufacturer() {
        final Aggregation findAllManufactureAggregation = newAggregation(
                Aggregation.unwind("product"),
                Aggregation.group().addToSet("product.manufacturer").as("manufacture")
        );
        final String manufacture;
        try {
            manufacture = mongoTemplate.aggregate(findAllManufactureAggregation, "products", String.class).getMappedResults().get(0);
        } catch (IndexOutOfBoundsException err) {
            return Optional.empty();
        }
        return Optional.of(manufacture);
    }

    @Override
    public Optional<String> findAllWarehouse() {
        final Aggregation findAllWarehouseAggregation = newAggregation(
                Aggregation.unwind("product"),
                Aggregation.group().addToSet("product.warehouse").as("warehouse")
        );
        final String manufacture;
        try {
            manufacture = mongoTemplate.aggregate(findAllWarehouseAggregation, "products", String.class).getMappedResults().get(0);
        } catch (IndexOutOfBoundsException err) {
            return Optional.empty();
        }
        return Optional.of(manufacture);
    }

    @Override
    public Optional<String> findMinAndMaxPrice() {

        final Aggregation findMinMaxAggregation = newAggregation(
                Aggregation.unwind("product"),
                Aggregation.group()
                        .min("product.price").as("min")
                        .max("product.price").as("max")

        );
        final String minMaxPrice;
        try {
            minMaxPrice = mongoTemplate.aggregate(findMinMaxAggregation, "products", String.class).getMappedResults().get(0);
        } catch (IndexOutOfBoundsException err) {
            return Optional.empty();
        }
        return Optional.of(minMaxPrice);
    }
}
