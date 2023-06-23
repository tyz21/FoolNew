package com.example.toxicapplication.appUser.userProfile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileUserController {
    private final ProfileUserService profileUserService;

    @GetMapping("search/{requestSearch}")
    public List<ProfileUserEntity> requestSearch(@PathVariable String requestSearch) {
        return profileUserService.searchUser(requestSearch);
    }

    @GetMapping("/top-user")
    public List<ProfileUserEntity> getAllUser() {
        return profileUserService.getAllTopUsers();
    }

    @GetMapping("get/{idProfile}")
    public ProfileUserEntity getProfile(@PathVariable Long idProfile) {
        return profileUserService.getProfile(idProfile);
    }
}