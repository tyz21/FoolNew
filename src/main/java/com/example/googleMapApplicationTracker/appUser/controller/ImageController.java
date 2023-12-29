package com.example.googleMapApplicationTracker.appUser.controller;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
import com.example.googleMapApplicationTracker.appUser.service.ImageService;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;
    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    @DeleteMapping("/delete/{idPhoto}")
    public void deletePhoto(@PathVariable Long idPhoto) {
        imageService.removePhoto(idPhoto);
    }

     @PostMapping("/save")
    public ApiResponse<String> saveImage(@AuthenticationPrincipal AppUser appUser,
                                         @RequestParam("image") MultipartFile image) {
        return imageService.saveImage(appUser, image);
    }

    @GetMapping("/{userId}")
    public ApiResponse<String> getImageByAppUserId(@PathVariable("userId") Long userId) {
        return imageService.getImageByAppUserId(userId);
    }
}
