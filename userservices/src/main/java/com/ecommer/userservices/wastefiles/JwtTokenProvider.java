//package com.ecommer.userservices.security.auth2server.system.justpracticejwtfilters;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtTokenProvider {
//    private String secretKey = "your-secret-key";  // Replace with your secret key
//
//    // Method to resolve token from the request header
//    public String resolveToken(HttpServletRequest request) {
//        // Extract token from Authorization header (e.g., "Bearer <token>")
//        String token = request.getHeader("Authorization");
//        if (token != null && token.startsWith("Bearer ")) {
//            return token.substring(7);  // Remove "Bearer " part and return the token
//        }
//        return null;  // Return null if no token is found or it's incorrectly formatted
//    }
//
//    // Method to validate the token
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // Method to extract authentication information from the JWT
//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//        String username = claims.getSubject();
//
//        List<SimpleGrantedAuthority> authorities = ((List<?>) claims.get("role"))
//                .stream()
//                .map(role -> new SimpleGrantedAuthority((String) role))
//                .collect(Collectors.toList());
//
//        return new UsernamePasswordAuthenticationToken(username, "", authorities);
//    }
//}
//
