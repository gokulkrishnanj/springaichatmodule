package com.example.springaichatmodel.Configuration.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomGatewayAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader("X-USERID");
        String userRoles = request.getHeader("X-ROLES");
        if(userId!=null && userRoles!=null){
            String[] userRoleArray = userRoles.split(",");
            List<String> userRolesList = Arrays.stream(userRoleArray).map(String::trim).collect(Collectors.toList());
             Authentication authentication = new CustomGatewayAuthentication(userId, userRolesList);
             SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        try{
            filterChain.doFilter(request,response);
        }
        catch(Exception e){}
        finally {
            SecurityContextHolder.clearContext();
        }
    }
}
