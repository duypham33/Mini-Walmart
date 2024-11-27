package com.beaconfire.shoppingapp.security;

import java.util.List;
import com.beaconfire.shoppingapp.services.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String jwtToken = authentication.getCredentials().toString();
        AuthUserDetail userDetail = userService.getSubject(jwtToken);
        // System.out.println(email);
        return userDetail == null
                ? null
                : new CustomAuthentication(userDetail);
    }

}