package com.example.googleMapApplicationTracker.registration;

import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/registration", produces = "application/json")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    @CrossOrigin
    @PostMapping()
    public ApiResponse<String> register(@RequestBody RegistrationRequest request) {
        String registrationResult = registrationService.register(request);

        if(request.getUserName().equals("") || request.getPassword().equals("")){
            return new ApiResponse<>("Exception: username or password is null", true);
        }

        if ("Username already exists".equals(registrationResult)) {
            return new ApiResponse<>("Exception: " + registrationResult, true);
        }
        return new ApiResponse<>("Success!", false);
    }
}