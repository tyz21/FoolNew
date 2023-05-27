package com.example.toxicapplication.login;

import com.example.toxicapplication.appUser.AppUserService;
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
    public String login(@RequestParam String email, @RequestParam String password) {
        try {
            UserDetails userDetails = appUserService.loadUserByUsername(email);
//ToDO loadUserByUsername by UserName

            if (!userDetails.isEnabled()) {
                return "confirm your email";
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    password
            );

            Authentication authenticated = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            return "Login successful!";
        } catch (AuthenticationException e) {
            return "check email or password";
        }
    }
}
