package com.example.googleMapApplicationTracker.appUser.controller;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
import com.example.googleMapApplicationTracker.appUser.service.ImageService;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "https://gamefool.gamefi.codes")
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
                                         @RequestBody MultipartFile image) {

        try {
            System.out.println("user id" + appUser.getId());
            System.out.println("image" + image);
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
         System.out.println("error 1");
        return imageService.saveImage(appUser, image);
    }
    @RequestMapping(value = "/save", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptionsRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "https://gamefool.gamefi.codes");
        headers.add("Access-Control-Allow-Methods", "POST");
        headers.add("Access-Control-Allow-Headers", "Content-Type");

        return ResponseEntity.ok().headers(headers).build();
    }
    @GetMapping("/{userId}")
    public ApiResponse<String> getImageByAppUserId(@PathVariable("userId") Long userId) {
        return imageService.getImageByAppUserId(userId);
    }
}
