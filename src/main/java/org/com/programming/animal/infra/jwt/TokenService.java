package org.com.programming.animal.infra.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class TokenService {

    private static final String SECRET_KEY = "f47d1fb5d8bb9c386302d2920843bde7ab4093921590bda77fe0361c27aff9a6";

    /* Extrair o e-mail do usuario */
    public String extractEmailUser(String email){
        return extractInfomaction(email, Claims::getSubject);
    }

    public<T> T extractInfomaction(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Objects> extractClaim, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extractClaim)
                .setSubject(userDetails.getUsername()) // -> Na classe ele já retorna o e-mail do usuário.
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(signatureToken(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean ifTokenValid(String token, UserDetails userDetails){
        final String userEmail = extractEmailUser(token);
        return (userEmail.equals(userDetails.getUsername()) && !ifTokenExpired(token));
    }
    public boolean ifTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractInfomaction(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signatureToken())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key signatureToken(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
