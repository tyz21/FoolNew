package com.example.googleMapApplicationTracker.appUser.service;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
import com.example.googleMapApplicationTracker.appUser.entity.Image;
import com.example.googleMapApplicationTracker.appUser.repository.AppUserRepository;
import com.example.googleMapApplicationTracker.appUser.repository.ImageRepository;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static com.example.googleMapApplicationTracker.appUser.utility.CompressImage.compressImage;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final AppUserRepository appUserRepository;

    public void removePhoto(Long idImage) {
        Image image = imageRepository.findById(idImage).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        imageRepository.delete(image);
    }

    public ApiResponse<String> saveImage(Long id, MultipartFile image) {
        System.out.println("error 2");
        var appUser = appUserRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        try {
            Image newImage = new Image();
            byte[] imageBytes = image.getBytes();
            newImage.setImage(compressImage(imageBytes));
            appUser.setImage(newImage);
            imageRepository.save(newImage);
            appUserRepository.save(appUser);
            System.out.println("error 3");
            return new ApiResponse<>("Success!", false, appUser.getId(), appUser.getUsername(), appUser.getImage().getImage());
        } catch (Exception e) {
            System.out.println("error 4");
            return new ApiResponse<>(e.toString(), true, id, appUser.getUsername(), appUser.getImage().getImage());
        }
    }
    @Transactional(readOnly = true)
    public ApiResponse<String> getImageByAppUserId(Long userId) {
        try {
            var appUser = appUserRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return new ApiResponse<>("Success!", false, appUser.getId(), appUser.getUsername(), appUser.getImage().getImage());
        } catch (Exception e) {
            return new ApiResponse<>(e.toString(), true);
        }
    }
}

