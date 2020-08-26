package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")
public class Products {
    protected List<Product> product;
}
