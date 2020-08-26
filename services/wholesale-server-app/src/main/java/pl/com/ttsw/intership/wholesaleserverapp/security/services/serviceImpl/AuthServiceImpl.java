package pl.com.ttsw.intership.wholesaleserverapp.security.services.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.Role;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.enums.RoleName;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.RoleRepository;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.repository.postgres.UserRepository;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.role.RoleNotFoundException;
import pl.com.ttsw.intership.wholesaleserverapp.security.exception.user.EmailAlreadyExistException;
import pl.com.ttsw.intership.wholesaleserverapp.security.jwt.JwtTokenProvider;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.ApiResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.JwtAuthenticationResponse;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.Requests.LoginRequest;
import pl.com.ttsw.intership.wholesaleserverapp.security.payload.Requests.RegisterRequest;
import pl.com.ttsw.intership.wholesaleserverapp.security.services.AuthService;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtTokenProvider.generateToken(auth);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setAccessToken(jwt);
        return response;
    }

    @Override
    public ApiResponse register(RegisterRequest registerRequest) throws NullPointerException{
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new EmailAlreadyExistException("Email: '"+registerRequest.getEmail()+"' already exist!");
        }
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setStreet(registerRequest.getStreet());
        user.setPostCode(registerRequest.getPostCode());
        user.setCity(registerRequest.getCity());
        user.setNip(registerRequest.getNip());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> {
                    throw new RoleNotFoundException("Role not found!");
                });
        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);
        return new ApiResponse(true, "User registered!");
    }
}
