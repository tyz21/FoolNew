package com.example.googleMapApplicationTracker.appUser.controller;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
import com.example.googleMapApplicationTracker.appUser.entity.Image;
import com.example.googleMapApplicationTracker.appUser.repository.AppUserRepository;
import com.example.googleMapApplicationTracker.appUser.repository.ImageRepository;
import com.example.googleMapApplicationTracker.appUser.service.ImageService;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private static final String SAVE_DIR = "/";

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
    public String upload(
            @RequestParam(value = "file", required = true) MultipartFile file)
    {

        System.out.println(file);
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
                return "You successfully uploaded !";
            } catch (Exception e) {
                return "You failed to upload   => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + " because the file was empty.";
        }
    }

    private String addPadding(String base64Image) {
        int padding = 4 - base64Image.length() % 4;
        return base64Image + "=".repeat(padding);
    }

    @Transactional(readOnly = true)
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> getImageByAppUserId(@RequestParam(value = "id", required = true)  Long id) {
        return imageService.getImageByAppUserId(id);
    }
}
