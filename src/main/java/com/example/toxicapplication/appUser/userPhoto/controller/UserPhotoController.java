package com.example.toxicapplication.appUser.userPhoto.controller;

import com.example.toxicapplication.appUser.userPhoto.service.UserPhotoService;
import com.example.toxicapplication.exception.NoPhotoForProfileException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserPhotoController {
    private final UserPhotoService userPhotoService;

    @GetMapping("id/profile/{idProfile}")
    public long getIdProfileUser(@PathVariable Long idProfile) {
        return userPhotoService.getIdProfileUser(idProfile);
    }

    @GetMapping("/allImages/{profileId}")
    public List<Long> getAllIdImage(@PathVariable long profileId) throws NoPhotoForProfileException {
        return userPhotoService.getAllIdImage(profileId);
    }

}
