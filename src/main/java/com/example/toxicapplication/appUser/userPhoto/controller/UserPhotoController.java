package com.example.toxicapplication.appUser.userPhoto.controller;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.example.toxicapplication.appUser.userPhoto.service.UserPhotoService;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.exception.NoPhotoForProfileException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/images")
public class UserPhotoController {
    private final UserPhotoService userPhotoService;

//    @GetMapping("/randomUsers")
//    public List<ProfileUserEntity> getRandomUsers() throws NoPhotoForProfileException {
//        return userPhotoService.provideRandomUsers();
//    }
    @GetMapping("/profile/{profileId}")
    public List<UserPhotoEntityDemo> getALlPhotoData(@PathVariable Long profileId)  {
        return userPhotoService.getALlPhotoData(profileId);
    }
}
