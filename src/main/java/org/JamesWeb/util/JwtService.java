package org.JamesWeb.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.JamesWeb.exception.UserEmailNotFoundException;
import org.JamesWeb.model.User_roles;
import org.JamesWeb.model.users;
import org.JamesWeb.repository.UserRepository;
import org.JamesWeb.repository.UserRolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    private UserRolesRepo userRolesRepo;
    @Autowired
    private UserRepository userRepository;
    private static final String SECRET_KEY =
            "mySuperSecretKeyForJwtAuthentication123456789";

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String userEmail) {

        users users = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserEmailNotFoundException("Invalid userEmail"));
        java.util.List<User_roles> userRolesList = userRolesRepo.findByUsersId(users.getId());
        
        // Only send role names, not entire objects
        java.util.List<String> roleNames = userRolesList.stream()
                .map(ur -> ur.getRole().getRole().name())
                .toList();

        return Jwts.builder()
                .subject(userEmail)
                .claim("roles", roleNames)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token,
                                 UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}