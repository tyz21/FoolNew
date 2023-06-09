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

    @GetMapping("/allIds/{profileId}")
    public List<Long> getAllPhotoIDs(@PathVariable Long profileId) {
        return profileUserService.getAllPhotoIDs(profileId);
    }
    @GetMapping("/getProfileId/{id}")
    public Long getProfileId(@PathVariable Long id){
        return profileUserService.getProfileId(id);
    }
    //    @GetMapping("/{profileId}")
//    public ProfileUserEntity getProfileUser(@PathVariable Long profileId) {
//        return profileUserService.getProfile(profileId);
//    }
    @GetMapping("/getRatingPhoto/{photoId}")
    public double getRating(@PathVariable long photoId) {
        return profileUserService.getRatingPhoto(photoId);
    }

    @GetMapping("/getTopPhoto/{photoId}")
    public long getTop(@PathVariable long photoId) {
        return profileUserService.getTop(photoId);
    }
    @GetMapping("/top-user") //s
    public String getAllUser(){
        return profileUserService.getAllUser();
    }
    @GetMapping("/username/{idProfile}")
    public String getUserName(@PathVariable Long idProfile){
        return profileUserService.getUserName(idProfile);
    }
 }