package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.RoleName;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Enumerated(EnumType.STRING)
    private RoleName name;
}
