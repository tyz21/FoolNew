package com.example.toxicapplication.login;

import com.example.toxicapplication.appUser.userDetails.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {
    private final AppUserService appUserService;
    private AuthenticationManager authenticationManager;

    @GetMapping()
    public String login(@RequestParam String userName, @RequestParam String password) {
        try {
            UserDetails userDetails = appUserService.loadUserByUsername(userName);

            if(userName.isEmpty() || password.isEmpty()){
                return "userName or password is empty";
            }

            if (!userDetails.isEnabled()) {
                return "confirm your email";
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userName,
                    password
            );

            Authentication authenticated = authenticationManager.authenticate(authentication);
            if (!authenticated.isAuthenticated()) {
                return "user not authenticated";
            }

            SecurityContextHolder.getContext().setAuthentication(authenticated);
            return "Login successful!";

        } catch (AuthenticationException e) {
            return "check username or password";
        }
    }
}
