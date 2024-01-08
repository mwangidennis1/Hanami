package com.example.hanami.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
    @Value("${secret.key}")
    private  static String SECRET_KEY;

    public String extractUsernameClaims(String jwt){
     String username=extractClaim(jwt,Claims::getSubject);
     return username;
    }
    //Method to extract single claim
    private <T> T extractClaim(String jwt, Function<Claims,T> resolveClaims){
       final Claims claims=extractClaims(jwt);
       return resolveClaims.apply(claims);
    }
    //Method to generate token with claims
    public String generateToken(Map<String ,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*60*100*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public String generateToken(Map<String ,Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*60*100*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    //Method to generate token without claims
    public String generateToken(UserDetails userDetails){

        return generateToken(new HashMap<>(),userDetails);
    }
    //method to check if token is valid
    public boolean isTokenValid(String jwt,UserDetails userDetails){
        String username=extractUsernameClaims(jwt);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }
    public boolean  isTokenExpired(String jwt){
        return extractExpiry(jwt).before(new Date());
    }

    private Date extractExpiry(String jwt) {
        return extractClaim(jwt,Claims::getExpiration);
    }

    //Method to extract claims
    public Claims  extractClaims(String jwt){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

    }
   private Key getSigningKey(){
     byte[]  keys= Decoders.BASE64.decode(SECRET_KEY);
     return Keys.hmacShaKeyFor(keys);
   }
}
