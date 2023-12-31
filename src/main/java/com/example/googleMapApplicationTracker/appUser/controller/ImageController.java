package com.example.googleMapApplicationTracker.appUser.controller;

import com.example.googleMapApplicationTracker.appUser.entity.Image;
import com.example.googleMapApplicationTracker.appUser.repository.ImageRepository;
import com.example.googleMapApplicationTracker.appUser.service.ImageService;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "https://gamefool.gamefi.codes")
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @DeleteMapping("/delete/{idPhoto}")
    public void deletePhoto(@PathVariable Long idPhoto) {
        imageService.removePhoto(idPhoto);
    }

    @ResponseBody
    @RequestMapping(value = "/save", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST)
    public ApiResponse<String> upload(
            @RequestParam(value = "file", required = true) MultipartFile file)
    {
        Image newImage = new Image();
        try {
            newImage.setImage(file.getBytes());
            imageRepository.save(newImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!file.isEmpty()) {
            try {
                byte[] fileContent = file.getBytes();
                return new ApiResponse<>("Success!", false);
            } catch (Exception e) {
                return new ApiResponse<>( e.getMessage(), true);
            }
        } else {
            return new ApiResponse<>("You failed to upload, because the file was empty", true);
        }
    }

    @Transactional(readOnly = true)
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> getImageByAppUserId(@RequestParam(value = "id", required = true)  Long id) {
        return imageService.getImageByAppUserId(id);
    }
}
