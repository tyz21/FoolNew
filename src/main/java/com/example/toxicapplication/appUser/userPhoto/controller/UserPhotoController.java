package com.example.toxicapplication.appUser.userPhoto.controller;

import com.example.toxicapplication.appUser.userDetails.AppUser;
import com.example.toxicapplication.appUser.userDetails.AppUserRepository;
import com.example.toxicapplication.appUser.userPhoto.service.UserPhotoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserPhotoController {
    private final UserPhotoService userPhotoService;
    private  final AppUserRepository appUserRepository;

    @GetMapping("id/{userName}")
    public long getIdUser(@PathVariable String userName){
        return userPhotoService.getIdUser(userName);
    }

    @PostMapping("/profile-image/{userId}")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long userId, @RequestParam("image") MultipartFile file) throws IOException {
        AppUser appUser = appUserRepository.findById(userId).orElse(null);
        if (appUser == null) {
            return new ResponseEntity<>("Invalid user ID", HttpStatus.BAD_REQUEST);
        }

        byte[] imageBytes = file.getBytes();
        String imagePath = userPhotoService.saveImageRectangle(appUser, imageBytes, file.getOriginalFilename());
        return new ResponseEntity<>("Image uploaded successfully! Image path: " + imagePath, HttpStatus.OK);
    }

    // ToDO make save photo in byte[] in dataBase
    @GetMapping("/profile-image/{id}")
    public ResponseEntity<byte[]> getProfileImageById(@PathVariable Long id) throws IOException {
        byte[] imageBytes = userPhotoService.getProfileImageReqtangleById(id);
        if (imageBytes == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/allImages/{profileId}")
    public List<Long> getAllIdImage(@PathVariable Long profileId){
        return userPhotoService.getAllIdImage(profileId);
    }

}