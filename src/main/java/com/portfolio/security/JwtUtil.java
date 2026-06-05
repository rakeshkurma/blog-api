package com.portfolio.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtil {

    private final String SECRET_STRING="MySuperSecretKeyForBloggingAPPThatIsAtleast32CharactersLong";

    private final SecretKey key= Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    private final long EXPIRATION_TIME=600;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();

    }
}