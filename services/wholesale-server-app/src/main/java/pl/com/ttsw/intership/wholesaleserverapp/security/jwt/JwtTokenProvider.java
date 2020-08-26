package pl.com.ttsw.intership.wholesaleserverapp.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.com.ttsw.intership.wholesaleserverapp.security.UserPrincipal;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecretKey;

    @Value("${app.jwtExpirationInMs}")
    private int expiate;


    public String generateToken(Authentication auth) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        Date now = new Date();
        Date expDate = new Date(now.getTime() + expiate);

        return Jwts.builder()
                .setSubject(Long.toString(principal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }


    public Long getUserIdFromToken(String token) {
        Claims c = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(c.getSubject());
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature! Message - {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token! Message - {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token! Message - {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token! Message - {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty! Message - {}", e.getMessage());
        }
        return false;
    }


}
