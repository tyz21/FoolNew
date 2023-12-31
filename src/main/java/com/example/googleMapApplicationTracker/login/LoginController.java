package com.example.googleMapApplicationTracker.login;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
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
@CrossOrigin(origins = "https://gamefool.gamefi.codes")
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
            AppUser appUser;
            var existsUser = appUserRepository.findByUserName(userName);
            if (existsUser.isEmpty()){
                return new ApiResponse<>("no such user", true);
            } else {
                appUser = existsUser.get();
            }
            Authentication authenticated = authenticationManager.authenticate(authentication);
            if (!authenticated.isAuthenticated()) {
                return new ApiResponse<>("user not authenticated", true);
            }

            if(appUser.getImage().getImage() == null) {
                 byte[] newByte = new byte[1];
                 newByte[0] = 1;
                return new ApiResponse<>("Success!", false, appUser.getId(), appUser.getUsername(), newByte);
            }
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            return new ApiResponse<>("Success!", false, appUser.getId(), appUser.getUsername(), appUser.getImage().getImage());

        } catch (AuthenticationException e) {
            return new ApiResponse<>("check username or password", true);
        }
    }
}
