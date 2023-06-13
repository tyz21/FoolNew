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

    @GetMapping("/getProfileId/{id}")
    public Long getProfileId(@PathVariable Long id){
        return profileUserService.getProfileId(id);
    }
    @GetMapping("/getTopPhoto/{photoId}")
    public long getTop(@PathVariable long photoId) {
        return profileUserService.getTop(photoId);
    }
    @GetMapping("/top-user") //s
    public String getAllUser(){
        return profileUserService.getAllTopUser();
    }
    @GetMapping("/username/{idProfile}")
    public String getUserName(@PathVariable Long idProfile){
        return profileUserService.getUserName(idProfile);
    }
    @GetMapping("id/profile/{idProfile}")
    public long getIdProfileUser(@PathVariable Long idProfile) {
        return profileUserService.getIdProfileUser(idProfile);
    }

}