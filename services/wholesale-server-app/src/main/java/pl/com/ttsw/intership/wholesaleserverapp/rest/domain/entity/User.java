package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String street;
    @Column(name = "post_code")
    private String postCode;
    private String city;
    private String nip;
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @NotEmpty(message = "Roles must be not null")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @JsonManagedReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Order> orderList;
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Warehouse> warehouseList;
    @Column(name = "warehouse_worker")
    private Long warehouseWorker;
}
