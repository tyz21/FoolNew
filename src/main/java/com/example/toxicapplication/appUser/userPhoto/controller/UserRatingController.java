package com.example.toxicapplication.appUser.userPhoto.controller;

import com.example.toxicapplication.appUser.userPhoto.service.UserRatingService;
import com.example.toxicapplication.exception.NoPhotoForProfileException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/rating")
public class UserRatingController {
    private final UserRatingService userRatingService;

    @PostMapping("/{photoId}")
    public double postRating(@PathVariable Long photoId, @RequestParam("rating") Double rating) throws NoPhotoForProfileException {
        return userRatingService.postRating(photoId, rating);
    }
    @GetMapping("/{photoId}")
    public double getRating(@PathVariable long photoId) {
        return userRatingService.getRatingPhoto(photoId);
    }
}
