package pl.com.ttsw.intership.wholesaleserverapp.rest.specification;

import org.bson.types.Decimal128;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

@Component
public class ProductSpecification {
    public List<AggregationOperation> toPredicate(Product product) {
        List<AggregationOperation> aggregationOperationList = new ArrayList<>();
        aggregationOperationList.add(Aggregation.unwind("product"));
        if (nonNull(product.getSearch())) {
            aggregationOperationList.add(
                    Aggregation.match(new Criteria().orOperator(
                            Criteria.where("product.name").regex(Pattern.compile(product.getSearch(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                            Criteria.where("product.warehouse").regex(Pattern.compile(product.getSearch(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                            Criteria.where("product.manufacturer").regex(Pattern.compile(product.getSearch(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
                    )));
        }
        if (!CollectionUtils.isEmpty(product.getManufactureFilter())) {
            aggregationOperationList.add(Aggregation.match(Criteria.where("product.manufacturer").in(product.getManufactureFilter())));
        }
        if (!CollectionUtils.isEmpty(product.getWarehouseFilter())) {
            aggregationOperationList.add(Aggregation.match(Criteria.where("product.warehouse").in(product.getWarehouseFilter())));
        }
        if (nonNull(product.getFromPrice())) {
            aggregationOperationList.add(Aggregation.match(Criteria.where("product.price").gte(new Decimal128(product.getFromPrice()))));
        }
        if (nonNull(product.getToPrice())) {
            aggregationOperationList.add(Aggregation.match(Criteria.where("product.price").lte(new Decimal128(product.getToPrice()))));
        }
        if (nonNull(product.getToPrice())) {
            aggregationOperationList.add(Aggregation.match(Criteria.where("product.price").lte(new Decimal128(product.getToPrice()))));
        }
        aggregationOperationList.add(Aggregation.project("product._id", "product.name", "product.image", "product.manufacturer", "product.warehouse", "product.price", "product.maxAmount"));
        return aggregationOperationList;
    }
}
