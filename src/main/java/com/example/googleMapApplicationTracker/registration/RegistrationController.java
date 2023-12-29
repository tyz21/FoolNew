package com.example.googleMapApplicationTracker.registration;

import com.example.googleMapApplicationTracker.appUser.repository.AppUserRepository;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/registration", produces = "application/json")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final AppUserRepository appUserRepository;
    @PostMapping()
    public ApiResponse<String> register(@RequestBody RegistrationRequest request) {
         registrationService.register(request);

        if (request.getUserName().equals("") || request.getPassword().equals("")){
            return new ApiResponse<>("Exception: username or password is null", true);
        }

        return new ApiResponse<>("Success!", false, 0, request.getUserName(), null);
    }
}