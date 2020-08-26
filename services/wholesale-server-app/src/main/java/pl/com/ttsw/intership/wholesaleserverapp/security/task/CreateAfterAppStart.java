package pl.com.ttsw.intership.wholesaleserverapp.security.task;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Role;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.RoleName;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.RoleRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.role.RoleNotFoundException;

import org.apache.commons.text.*;
import static org.apache.commons.text.CharacterPredicates.LETTERS;
import static org.apache.commons.text.CharacterPredicates.DIGITS;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

@Data
@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class CreateAfterAppStart {
    @NonNull private final RoleRepository roleRepository;
    @NonNull private final UserRepository userRepository;
    @NonNull private final PasswordEncoder passwordEncoder;

    @Value("${app.sadmin.email}")
    private String S_EMAIL;


    @EventListener(ApplicationReadyEvent.class)
    public void executeAfterAppStartup() {
        createSuperAdminAfterStartup();
    }

    @Transactional
    void createSuperAdminAfterStartup(){
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> {
                    throw new RoleNotFoundException("Role Admin not found!");
                });

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> {
                    throw new RoleNotFoundException("Role User not found!");
                });
        Optional<User> sAdmin = userRepository.findByEmail(S_EMAIL);
        if(sAdmin.isEmpty()) {
            User admin = new User();
            String GENERATED_PASSWORD = generateRandomPassword(40);

            admin.setName("SADMIN");
            admin.setEmail(S_EMAIL);
            admin.setPassword(passwordEncoder.encode(GENERATED_PASSWORD));
            admin.setStreet("");
            admin.setPostCode("");
            admin.setCity("");
            admin.setNip("");
            admin.setPhoneNumber("");


            admin.setRoles(Set.of(adminRole, userRole));

            userRepository.save(admin);
            log.info("Super admin created login: {} , password: {}", S_EMAIL, GENERATED_PASSWORD);

            try {
                String logs = "Login: "+S_EMAIL+" password: "+GENERATED_PASSWORD;
                Path path = Paths.get("password");
                byte[] strToBytes = logs.getBytes();
                Files.write(path, strToBytes);
            } catch (IOException e) {
                log.error("{}", e.getMessage());
            }
        }
    }

    private String generateRandomPassword(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();
        return pwdGenerator.generate(length);
    }
}
