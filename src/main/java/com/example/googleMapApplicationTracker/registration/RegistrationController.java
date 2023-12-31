package com.example.googleMapApplicationTracker.registration;

import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/registration", produces = "application/json")
@CrossOrigin(origins = "https://gamefool.gamefi.codes")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping()
    public ApiResponse<String> register(@RequestBody RegistrationRequest request) {

        if (request.getUserName().equals("") || request.getPassword().equals("")) {
            return new ApiResponse<>("Exception: username or password is null", true);
        }
        return registrationService.register(request);
        // return new ApiResponse<>("Success!", false, 0, request.getUserName(), null);
    }
}