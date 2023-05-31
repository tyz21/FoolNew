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
    @GetMapping("/rating/{profileId}")
    public double getRating(@PathVariable Long profileId){
        return profileUserService.getRating(profileId);
    }
    @GetMapping("/allIds/{profileId}")
    public List<Long> getAllPhotoIDs(@PathVariable Long profileId) {
        return profileUserService.getAllPhotoIDs(profileId);
    }

    @GetMapping("/{profileId}")
    public ProfileUserEntity getProfileUser(@PathVariable Long profileId) {
        return profileUserService.getProfile(profileId);
    }

}