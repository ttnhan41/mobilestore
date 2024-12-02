package com.nhan.mobilestore.security;

import com.nhan.mobilestore.entity.User;
import com.nhan.mobilestore.exception.InvalidTokenException;
import com.nhan.mobilestore.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

     /*
     * Generate JWT Token
     * @param authentication
     * @return String
     */
    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        var user = (User) authentication.getPrincipal();

        // jwtExpirationInMs * 1000L, it converts it to milliseconds.
        // 3600 seconds × 1000 milliseconds = 3,600,000 milliseconds.
        // This means the token will expire 1 hour (60 minutes) from the time it was created.
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationInMs *
                1000L);

        return Jwts.builder()
                .claims(claims)
                .claim("id", user.getId())
                .subject(user.getUsername())
                .claim("role", user.getRole().getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .signWith(secretKey, Jwts.SIG.HS256).compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("JWT token is expired");
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid JWT token");
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
