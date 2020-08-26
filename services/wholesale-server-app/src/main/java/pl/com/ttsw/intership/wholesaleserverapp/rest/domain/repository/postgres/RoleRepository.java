package pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Role;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(@Param("name") RoleName roleName);
}
