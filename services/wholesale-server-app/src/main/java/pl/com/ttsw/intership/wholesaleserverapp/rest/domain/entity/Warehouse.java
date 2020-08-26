package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long warehouseId;
    @NotBlank(message = "Name must be not null")
    @Column(name = "warehouse_name", unique = true)
    private String warehouseName;
    @NotBlank(message = "Street must be not null")
    private String street;
    @NotBlank(message = "PostCode must be not null")
    @Column(name = "post_code")
    private String postCode;
    @NotBlank(message = "City must be not null")
    private String city;
    @NotBlank(message = "Nip must be not null")
    private String nip;
    @NotBlank(message = "PhoneNumber must be not null")
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


}