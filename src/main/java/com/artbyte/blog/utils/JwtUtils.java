package com.artbyte.blog.utils;

import com.artbyte.blog.repository.RoleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private static final String secretKey = "zdhrS$RGBsea4YHEZ5se45THy5s$";

    public String generateToken(UserDetails userDetails){
        JwtBuilder jwt = Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", extractRole(userDetails))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, generateJwtSecretKey());
        return jwt.compact();
    }

    public SecretKey generateJwtSecretKey(){
        byte[] keyBytes = secretKey.getBytes();
        byte[] keyBytesPadded = new byte[32];
        System.arraycopy(keyBytes, 0, keyBytesPadded, 0, Math.min(keyBytes.length, 32));
        return Keys.hmacShaKeyFor(keyBytesPadded);
    }

    private String extractRole(UserDetails userDetails){
        String role = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("UNKNOW");
        return "ROLE_" + role;
    }

    public boolean validateToken(String token, String username){
        return (username.equals(getUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    public String getUsername(String token){
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(generateJwtSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
