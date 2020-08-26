package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @Value("date_finalize")
    private Date dateFinalize;
    @Value("date_sell")
    private Date dateSell;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<BasketItem> basketItemList;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Long totalAmount;
    private boolean active;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    @Column(name = "assigned_by")
    private Long assignedBy;
}