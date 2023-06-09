package com.example.toxicapplication.appUser.userPhoto.controller;

import com.example.toxicapplication.appUser.userPhoto.service.UserRatingService;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.exception.NoPhotoForProfileException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserRatingController {
    private final UserRatingService userRatingService;

    @PostMapping("/rating/{profileId}")
    public double postRating(@PathVariable Long profileId, @RequestParam("rating") Double rating) throws NoPhotoForProfileException {
        return userRatingService.postRating(profileId, rating);
    }

    @GetMapping("/idPhoto/randomId")
    public long getRandomId() throws NoPhotoForProfileException {
        return userRatingService.provideRandomIdPhotoUser();
    }

}
