package com.example.toxicapplication.appUser.userPhoto.controller;

import com.example.toxicapplication.appUser.userDetails.AppUser;
import com.example.toxicapplication.appUser.userPhoto.service.UserPhotoRectangleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.toxicapplication.utility.UserUtility.getHttpHeaders;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserPhotoRectangleController {
    private final UserPhotoRectangleService userPhotoRectangleService;

    @PostMapping("/download/profile-image-rectangle")
    public ResponseEntity<String> uploadProfileImage(@AuthenticationPrincipal AppUser appUser, @RequestParam("image") MultipartFile file) throws IOException {
        byte[] imageBytes = file.getBytes();
        String imagePath = userPhotoRectangleService.saveImageRectangle(appUser, imageBytes, file.getOriginalFilename());
        return new ResponseEntity<>("Image uploaded successfully! Image path: " + imagePath, HttpStatus.OK);
    }

    @GetMapping("get/profile-image-rectangle/{id}")
    public ResponseEntity<byte[]> getProfileImageById(@PathVariable Long id) throws IOException {
        byte[] imageBytes = userPhotoRectangleService.getProfileImageRectangleById(id);
        if (imageBytes == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = getHttpHeaders(imageBytes);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}
