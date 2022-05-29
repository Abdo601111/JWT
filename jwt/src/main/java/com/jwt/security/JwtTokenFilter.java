package com.jwt.security;

import com.jwt.api.Role;
import com.jwt.api.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

   @Autowired JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if(!hasAuthorizationHeader(request)){

            filterChain.doFilter(request,response);
         return;
        }
        String accessToken= getAccessToken(request);
        if(!jwtUtil.validateAccessToken(accessToken)){
            filterChain.doFilter(request,response);
            return;

        }

        setAuthenticationContext(accessToken,request);
        filterChain.doFilter(request,response);


    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest request) {

        UserDetails userDetails= getUserDetails(accessToken);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    }

    private UserDetails getUserDetails(String accessToken) {
        User userDetails=new User();
        Claims claims= jwtUtil.parseClaims(accessToken);
        String claimsRoles= (String) claims.get("roles");
        claimsRoles=claimsRoles.replace("[","").replace("]","");
        String[] rolesName=claimsRoles.split(",");
        for(String rName:rolesName){
            userDetails.addRole(new Role(rName));
        }
        String subject= (String) claims.get(Claims.SUBJECT);

        String[] subjectArray= subject.split(",");
        userDetails.setId(Integer.parseInt(subjectArray[0]));
        userDetails.setEmail(subjectArray[1]);
        return userDetails;

    }

    private boolean hasAuthorizationHeader(HttpServletRequest request){

        String header=request.getHeader("Authorization");

        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){

            return false;
        }

        return true;

    }

    private String getAccessToken(HttpServletRequest request){
        String header=request.getHeader("Authorization");
        String token = header.split(" ")[0].trim();
        return token;
    }
}
