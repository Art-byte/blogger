package com.artbyte.blog.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private static final String secretKey = "zdhrS$RGBsea4YHEZ5se45THy5s$";

    public String generateToken(String username){
        JwtBuilder jwt = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, generateJwtSecretKey());
        return jwt.compact();
    }

    public boolean validateToke(String token, String username){
        return (username.equals(getUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    private String getUsername(String token){
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(generateJwtSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public SecretKey generateJwtSecretKey(){
        byte[] keyBytes = secretKey.getBytes();
        byte[] keyBytesPadded = new byte[32];
        System.arraycopy(keyBytes, 0, keyBytesPadded, 0, Math.min(keyBytes.length, 32));
        return Keys.hmacShaKeyFor(keyBytesPadded);
    }

}
