package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "basket_items")
@Getter
@Setter
@NoArgsConstructor
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_item_id")
    private Long basketId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String image;
    private String manufacturer;
    private String name;
    private BigDecimal price;
    private long amount;
}
