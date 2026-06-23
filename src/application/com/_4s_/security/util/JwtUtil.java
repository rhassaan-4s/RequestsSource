package com._4s_.security.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private String secretKey; // must be at least 32 chars for HS256
    private long expiration;  // in ms (e.g. 3600000 = 1 hour)

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // --- TOKEN CREATION ---
    public String generateToken(String tenantId, String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tenantId", tenantId);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // --- EXTRACTION HELPERS ---
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractTenantId(String token) {
        return extractAllClaims(token).get("tenantId", String.class);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object roles = claims.get("roles");
        if (roles instanceof List<?>) {
            List<?> rawList = (List<?>) roles;
            List<String> result = new ArrayList<>();
            for (Object r : rawList) {
                result.add(r.toString());
            }
            return result;
        }
        return Collections.emptyList();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // --- INTERNAL ---
    private Claims extractAllClaims(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // --- CONFIG ACCESSORS ---
    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpiration() {
        return expiration;
    }
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}


//package com._4s_.security.util;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//
//public class JwtUtil {
//
////	@Value("${jwt.secret}")
//    private String secretKey;
//    
//	public String getSecretKey() {
//		return secretKey;
//	}
//
//
//
//	public void setSecretKey(String secretKey) {
//		this.secretKey = secretKey;
//	}
//
//
//
//	private String expiration;// = 1000 * 60 * 60 * 10; // 10 hours
//	
//    public String generateToken(String tenantId, String username) {
//    	Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//
//    	System.out.println("$$$$$$$$$$$$$Generating token: with secretKey: " + (secretKey != null ? secretKey : "null"));
//    	System.out.println("$$$$$$$$$$Generating token: tenantId: " + tenantId + ", username: " + username + ", expiration: " + expiration);
//    	Map<String, Object> claims = new HashMap<>();
//        claims.put("tenantId", tenantId);
//        
//        return Jwts.builder()
//        		.setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + new Long(expiration)))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    
//    
//    public String extractUsername(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    public String extractTenantId(String token) {
//        return extractAllClaims(token).get("tenantId", String.class);
//    }
//
//    private Claims extractAllClaims(String token) {
//    	Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//        return Jwts.parser()
//                .setSigningKey(key)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractAllClaims(token).getExpiration().before(new Date());
//    }
//
//	public String getExpiration() {
//		return expiration;
//	}
//
//
//
//	public void setExpiration(String expiration) {
//		this.expiration = expiration;
//	}
//
//
////
////	public String getSecretKey() {
////		return secretKey;
////	}
////
////
////	public void setSecretKey(Object secretKey) {
////		this.secretKey = secretKey + "";
////	}
////    
//    
//}
