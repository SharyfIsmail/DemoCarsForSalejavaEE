package com.example.demoCarsForSale.security;

import com.example.demoCarsForSale.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtil {
    private static final String CLAIMS_SUBJECT = "sub";
    private static final String CLAIMS_CREATED = "created";
    private static final Long TOKEN_VALIDITY = 604800L;
    private static final String TOKEN_SECRET = "APISecret123";

    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_SUBJECT, user.getEmail());
        claims.put(CLAIMS_CREATED, new Date());

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(generateExpirationDate())
            .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
            .compact();
    }

    public static boolean isTokenValid(String token, User user) {
        return getEmailFromToken(token)
            .equals(user.getEmail()) && isTokenExpired(token);
    }

    public static String getEmailFromToken(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimTFunction) {
        Claims claims = extractAllClaims(token);
        return claimTFunction.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SECRET)
            .parseClaimsJws(token)
            .getBody();
    }

    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + (TOKEN_VALIDITY * 1000));
    }

    private static boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration)
            .after(new Date());
    }
}

