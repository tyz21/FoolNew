package com.example.toxicapplication.appUser.userDetails;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class AppUserController {
    private final AppUserService appUserService;
    @GetMapping("id/user/{userName}")
    public long getIdUser(@PathVariable String userName) {
        return appUserService.getIdUser(userName);
    }

}
