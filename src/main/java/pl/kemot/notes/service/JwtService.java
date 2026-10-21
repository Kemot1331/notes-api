package pl.kemot.notes.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JwtService {
    @Value("${secret.key}")
    private String secretKey;

    @Value("${expire.time}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(Long userId) {
        JwtBuilder builder = Jwts.builder();
        builder.subject(userId.toString());
        builder.issuedAt(new Date());
        builder.expiration(new Date(System.currentTimeMillis() + expiration));
        builder.signWith(getSigningKey());

        return builder.compact();
    }
    public Long getUserIdFromToken(String token) {
        String idFromToken = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return Long.parseLong(idFromToken);
    }
}
