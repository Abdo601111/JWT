package com.jwt.security;

import com.jwt.api.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Scanner;

@Component
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(User user){
        return Jwts.builder()
                .setSubject(user.getId()+","+user.getEmail())
                .claim("roles",user.getRoles().toString())
                .setIssuer("abdoghareeb")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String token){

        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);

        return true;
        }catch (ExpiredJwtException ex){
            LOGGER.error("JWT Expired ", ex);
        }catch (IllegalArgumentException ex){
            LOGGER.error("Token Is Null, Empty Or Has Only Whitespace " ,ex);
        }catch (MalformedJwtException ex){
            LOGGER.error("JWT IS Invalid ",ex);
        }catch (UnsupportedJwtException ex){
            LOGGER.error("JWT Is not Supported",ex);
        }catch (SignatureException ex){
            LOGGER.error("Signature Invalid  Failed ",ex);
        }

        return false;
    }


    public String getSubject(String token){
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token){
        return  Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

    }


}
