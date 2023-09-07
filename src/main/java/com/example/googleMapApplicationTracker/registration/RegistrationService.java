package com.example.googleMapApplicationTracker.registration;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
import com.example.googleMapApplicationTracker.appUser.repository.AppUserRepository;
import com.example.googleMapApplicationTracker.appUser.entity.AppUserRole;
import com.example.googleMapApplicationTracker.appUser.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    public String register(RegistrationRequest request) {
        if (appUserRepository.existsByUserName(request.getUserName())) {
            return "Username already exists";
        }

        return appUserService.signUpUser(
                new AppUser(
                        request.getUserName(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}

