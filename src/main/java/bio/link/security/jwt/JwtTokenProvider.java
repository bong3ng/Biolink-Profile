package bio.link.security.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import bio.link.security.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	private final String JWT_SECRET = "ghtk";
<<<<<<< HEAD
	 
=======
	
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3
	private final Long JWT_EXPIRATION = 604800000L;
	
	public String generateToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		
		 return Jwts.builder()
                 .setSubject(Long.toString(userDetails.getUser().getId()))
                 .setIssuedAt(now)
                 .setExpiration(expiryDate)
                 .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                 .compact();
	}
	public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
<<<<<<< HEAD
    
    public Long getUserIdFromHeader(String jwt) {
    	String[] new_jwt = jwt.split("\\s");
    	jwt = new_jwt[1];
    	return this.getUserIdFromJWT(jwt);
    }
    
    
=======
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3
}
