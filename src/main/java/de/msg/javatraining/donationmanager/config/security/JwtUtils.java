package de.msg.javatraining.donationmanager.config.security;

import de.msg.javatraining.donationmanager.persistence.model.ERight;
import de.msg.javatraining.donationmanager.persistence.model.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  public List<String> revokedTokens = new ArrayList<>();

  @Value("${security.jwtSecret}")
  private String jwtSecret;

  @Value("${security.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(UserDetails userPrincipal, User user) {
    return generateTokenUsernameRights(userPrincipal.getUsername(), user);
  }

  public String generateTokenUsernameRights(String username, User user) {
    List<ERight> rights = new ArrayList<ERight>();
    user.getRoles().forEach(role -> {
      role.getRights().forEach((roleRight -> {
        if (!rights.contains(roleRight.getRoleRight())) {
          rights.add(roleRight.getRoleRight());
        }
      }));
    });

    return Jwts.builder().setSubject(username).claim("permissions", rights).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  public String generateTokenFromUsername(String username) {
    return Jwts.builder().setSubject(username).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public Object getRightsFromJwtToken(String token) {
    System.out.println(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody());
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("permissions");
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
  public void revokeToken(String token) {
    revokedTokens.add(token);
  }

  public boolean isTokenRevoked(String token) {
    return revokedTokens.contains(token);
  }
}

