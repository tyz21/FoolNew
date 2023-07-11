package com.example.toxicapplication.appUser.userPhoto.controller;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.example.toxicapplication.appUser.userPhoto.service.UserPhotoServiceDemo;
import com.example.toxicapplication.exception.NoPhotoForProfileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class UserPhotoControllerDemo {

    private final UserPhotoServiceDemo photoService;

    @Autowired
    public UserPhotoControllerDemo(UserPhotoServiceDemo photoService) {
        this.photoService = photoService;
    }

    @DeleteMapping("/delete/{idPhoto}")
    public void deletePhoto(@PathVariable Long idPhoto) {
        photoService.removePhoto(idPhoto);
    }

    @PostMapping("/upload")
    public String uploadPhoto(@AuthenticationPrincipal AppUser appUser,
                              @RequestParam("circle") MultipartFile circle,
                              @RequestParam("rectangle") MultipartFile rectangle) {
        if (circle.isEmpty() || rectangle.isEmpty()) {
            return "File is empty";
        }
        try {
            photoService.savePhoto(appUser, circle, rectangle);
            return "Photo uploaded successfully";
        } catch (IOException e) {
            return "Failed to upload photo";
        }
    }

    @GetMapping("/{type}/{photoId}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String type, @PathVariable Long photoId) throws NoPhotoForProfileException {
        UserPhotoEntityDemo photo = photoService.getPhotoById(photoId);
        // HttpHeaders headers = photoService.getCacheHeaders();
        if (photo == null) {
            throw new NoPhotoForProfileException("no photo");
        }

        if (type.equals("rectangle")) {
            return ResponseEntity.ok()
                    //.headers(headers)
                    .body(photo.getPhotoRectangle());
        } else if (type.equals("circle")) {
            return ResponseEntity.ok()
                    // .headers(headers)
                    .body(photo.getPhotoCircle());
        } else {
            throw new IllegalArgumentException("Invalid photo type");
        }
    }
}
