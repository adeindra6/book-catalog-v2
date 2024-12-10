package com.adeindra6.catalog.security.util;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import com.adeindra6.catalog.security.model.AccessJWTToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JWTTokenFactory {

    private final Key secret;
    
    public AccessJWTToken createAccessJWTToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().subject(username).add("scopes", authorities.stream().map(a -> a.getAuthority()).collect(Collectors.toList())).build();

        LocalDateTime currentTime = LocalDateTime.now();
        Date currentTimeDate = Date.from(currentTime.atZone(ZoneId.of("Asia/Jakarta")).toInstant());

        LocalDateTime expiredTime = currentTime.plusMinutes(15);
        Date expiredTimeDate = Date.from(expiredTime.atZone(ZoneId.of("Asia/Jakarta")).toInstant());

        String token = Jwts.builder().claims(claims).issuer("https://adeindra6.ceo").issuedAt(currentTimeDate).expiration(expiredTimeDate).signWith(secret).compact();

        return new AccessJWTToken(token, claims);
    }

}
