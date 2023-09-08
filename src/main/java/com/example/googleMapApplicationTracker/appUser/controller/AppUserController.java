package com.example.googleMapApplicationTracker.appUser.controller;

import com.example.googleMapApplicationTracker.appUser.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class AppUserController {
    private final AppUserService appUserService;
    @GetMapping("id/{userName}")
    public long getIdUser(@PathVariable String userName) {
        return appUserService.getIdUser(userName);
    }
}
