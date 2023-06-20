package com.example.toxicapplication.appUser.userDetails.controller;

import com.example.toxicapplication.appUser.userDetails.service.AppUserAdditionalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class AppUserController {
    private final AppUserAdditionalService appUserAdditionalService;
    @GetMapping("id/{userName}")
    public long getIdUser(@PathVariable String userName) {
        return appUserAdditionalService.getIdUser(userName);
    }
}
