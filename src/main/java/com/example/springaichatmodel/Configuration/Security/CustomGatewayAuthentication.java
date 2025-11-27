package com.example.springaichatmodel.Configuration.Security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CustomGatewayAuthentication extends AbstractAuthenticationToken {

    private String userId;
    private List<String> roles;

    public CustomGatewayAuthentication(String userId, List<String> roles) {
        super(roles
                .stream()
                .map(r-> new SimpleGrantedAuthority("ROLE_"+ r.trim()))
                .toList());
        this.userId = userId;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }
}
