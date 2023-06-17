package com.example.toxicapplication.appUser.userProfile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileUserController {
    private final ProfileUserService profileUserService;
    @GetMapping("/rating/{profileId}")
    public Double getRatingProfile(@PathVariable Long profileId) {
        return profileUserService.getRatingProfile(profileId);
    }
    @GetMapping("/getTopPhoto/{photoId}")
    public Long getTop(@PathVariable Long photoId) {
        return profileUserService.getTop(photoId);
    }
    @GetMapping("/top-user")
    public String getAllUser(){
        return profileUserService.getAllTopUser();
    }
    @GetMapping("/username/{idProfile}")
    public String getUserName(@PathVariable Long idProfile){
        return profileUserService.getUserName(idProfile);
    }

}