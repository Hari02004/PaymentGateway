package com.Fintech.fintech.project.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String secretKey = "myscretkeymysecretkeymysecretkeymyscretkey";
    private Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    public String generateToken(String userName) {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .issuedAt(new Date())
                .subject(userName)
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        ))
                .compact();
    }
    public String extractUsername(String token){

        return Jwts.parser()

                .verifyWith((javax.crypto.SecretKey) key)

                .build()

                .parseSignedClaims(token)

                .getPayload()

                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        try {

            Jwts.parser()

                    .verifyWith(
                            (javax.crypto.SecretKey) key
                    )

                    .build()

                    .parseSignedClaims(token);

            return extractUsername(token).equals(userDetails.getUsername());

        } catch (Exception e) {

            return false;
        }
    }
}
