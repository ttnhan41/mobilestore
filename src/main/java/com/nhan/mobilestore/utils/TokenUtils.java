package com.nhan.mobilestore.utils;

import com.nhan.mobilestore.entity.User;
import com.nhan.mobilestore.security.JwtTokenProvider;
import com.nhan.mobilestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public User getUser(String bearerToken) {
        String token = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        String username = tokenProvider.extractUsername(token);

        return userService.getUserByUsername(username).
                orElseThrow(() -> new BadCredentialsException("Username not found"));
    }
}
