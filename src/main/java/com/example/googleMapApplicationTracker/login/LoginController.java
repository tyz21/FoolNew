package com.example.googleMapApplicationTracker.login;

import com.example.googleMapApplicationTracker.appUser.repository.AppUserRepository;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class LoginController {
    private AuthenticationManager authenticationManager;
    private AppUserRepository appUserRepository;

    @GetMapping()
    public ApiResponse<String> login(@RequestParam String userName, @RequestParam String password) {
        try {
            if (userName.isEmpty() || password.isEmpty()) {
                return new ApiResponse<>("userName or password is empty", true);
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userName,
                    password
            );
            var existsUser = appUserRepository.findByUserName(userName).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );
            Authentication authenticated = authenticationManager.authenticate(authentication);
            if (!authenticated.isAuthenticated()) {
                return new ApiResponse<>("user not authenticated", true);
            }

            SecurityContextHolder.getContext().setAuthentication(authenticated);
            return new ApiResponse<>("Success!", false, existsUser.getId(), existsUser.getUsername(), null);

        } catch (AuthenticationException e) {
            return new ApiResponse<>("check username or password", true);
        }
    }
}
