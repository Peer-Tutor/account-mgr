package com.peertutor.AccountMgr.security;

import com.peertutor.AccountMgr.service.dto.AccountDTO;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);
    private static String JWT_SECRET;
    private static int JWT_EXPIRATION_MS;
    @Value("${app-config.jwtSecret}")
    private String jwtSecret;
    @Value("${app-config.jwtExpirationMs}")
    private int jwtExpirationMs;

    public static String generateJwtToken(AccountDTO accountDTO) {
        return generateTokenFromUsername(accountDTO.getName());
    }

    public static String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION_MS)).signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public static boolean validateJwtToken(String userName, String authToken) {
        try {
            String name = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken).getBody().getSubject();
            return name.equals(userName);
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

    @Value("${app-config.jwtSecret}")
    public void setJwtSecretStatic(String jwtSecret) {
        JWTUtils.JWT_SECRET = jwtSecret;
    }

    @Value("${app-config.jwtExpirationMs}")
    public void setJwtExpirationMsStatic(int jwtExpirationMs) {
        JWTUtils.JWT_EXPIRATION_MS = jwtExpirationMs;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
