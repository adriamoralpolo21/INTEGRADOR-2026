package com.glovo.app.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final Key key;
    private final long jwtExpirationMs;

    // 🔹 7 días para el refresh token (en milisegundos)
    private static final long REFRESH_EXPIRATION_MS = 7L * 24 * 60 * 60 * 1000;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long jwtExpirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
    }

    // ========= TOKEN DE ACCESO =========
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // email
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ========= REFRESH TOKEN (más largo) =========
    public String generateRefreshToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + REFRESH_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(email) // también usamos el email
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ========= EXTRACCIÓN =========

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ========= VALIDACIÓN =========

    // Para el filtro (usa UserDetails)
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Para refresh (solo quiero saber si el token es válido y no está vencido)
    public boolean validateToken(String token) {
        try {
            parseClaims(token); // si lanza excepción, es inválido
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ========= UTILS INTERNOS =========

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = parseClaims(token);
        return resolver.apply(claims);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
