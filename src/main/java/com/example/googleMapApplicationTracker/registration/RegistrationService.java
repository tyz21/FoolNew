package com.example.googleMapApplicationTracker.registration;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
import com.example.googleMapApplicationTracker.appUser.repository.AppUserRepository;
import com.example.googleMapApplicationTracker.appUser.entity.AppUserRole;
import com.example.googleMapApplicationTracker.appUser.service.AppUserService;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;
    public ApiResponse<String> register(RegistrationRequest request) {
        if (appUserRepository.existsByUserName(request.getUserName())) {
            return new ApiResponse<>("Username already exists", true);
        }
var newUser = new AppUser(
        request.getUserName(),
        request.getPassword(),
        AppUserRole.USER
);
        appUserService.signUpUser(newUser);

        return new ApiResponse<>("Success!", false, newUser.getId(), request.getUserName(), null);
    }
}

