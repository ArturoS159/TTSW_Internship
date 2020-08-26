package pl.com.ttsw.intership.wholesaleserverapp.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.com.ttsw.intership.wholesaleserverapp.rest.domain.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserPrincipal implements UserDetails {


    private Long id;

    private String name;

    private String password;

    private String email;
    private String street;
    private String postCode;
    private String city;
    private String nip;
    private String phoneNumber;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(){}

    public UserPrincipal(Long id,
                         String email,
                         String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetails create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map( role ->
                        new SimpleGrantedAuthority(role.getName().name())
                        ).collect(Collectors.toList());
        UserPrincipal principal = new UserPrincipal();

            principal.setId(user.getUserId());
            principal.setEmail(user.getEmail());
            principal.setName(user.getName());
            principal.setPassword(user.getPassword());
            principal.setStreet(user.getStreet());
            principal.setPostCode(user.getPostCode());
            principal.setCity(user.getCity());
            principal.setNip(user.getNip());
            principal.setPhoneNumber(user.getPhoneNumber());
            principal.setAuthorities(authorities);

        return principal;

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
