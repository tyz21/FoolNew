package com.example.toxicapplication.appUser.userPhoto.controller;

import com.example.toxicapplication.appUser.userPhoto.service.UserRatingService;
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
    public double getRating(@PathVariable Long profileId, @RequestParam("rating") double rating){
        return userRatingService.postRating(profileId, rating);
    }

    @GetMapping("/randomId")
    public long getRandomId(){
        return userRatingService.provideRandomId();
    }
}
