package com.plopez.diceroller.microservice.authservice.security;

import com.plopez.diceroller.microservice.authservice.model.dto.HttpRequestDTO;
import com.plopez.diceroller.microservice.authservice.model.entity.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JsonWebTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    AuthRouteValidator authRouteValidator;

    @PostConstruct
    protected  void initSecret() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(AuthUser authUser) {
        Map<String, Object> claims;
        claims = Jwts.claims().setSubject(authUser.getUserName());
        claims.put("role", authUser.getRole());
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validate(String token, HttpRequestDTO httpRequestDTO) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        if(!isAdmin(token) && authRouteValidator.isAdminPath(httpRequestDTO)) {
            return false;
        } else {
            return true;
        }
    }

    public String getUserNameFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return "Invalid token.";
        }
    }

    private boolean isAdmin(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("role").equals("admin");
    }

}
