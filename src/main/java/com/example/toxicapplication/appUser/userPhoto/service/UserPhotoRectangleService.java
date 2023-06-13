package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userDetails.AppUser;
import com.example.toxicapplication.appUser.userDetails.AppUserRepository;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.toxicapplication.utility.UserUtility.pathPhoto;
import static com.example.toxicapplication.utility.UserUtility.saveImageForProfile;

@Service
@Slf4j
@AllArgsConstructor
public class UserPhotoRectangleService {
    private final UserPhotoRepository userPhotoRepository;
    private final AppUserRepository appUserRepository;

    @Transactional
    public String saveImageRectangle(AppUser appUser, byte[] imageBytes, String imageName) throws IOException {
        String userFolder = "images/" + appUser.getId() + "/";
        String rectangleFolder = userFolder + "rectangle/";
        String imagePath = pathPhoto(imageBytes, imageName, rectangleFolder);

        appUser = appUserRepository.findById(appUser.getId()).orElse(appUser);

        UserPhotoEntity userPhotoEntity = new UserPhotoEntity(appUser, imagePath);
        userPhotoEntity.setProfileUserEntity(appUser.getProfileUserEntity());

        userPhotoRepository.save(userPhotoEntity);

        saveImageForProfile(appUser, userPhotoEntity);

        return imagePath;
    }


    @Transactional(readOnly = true)
    public byte[] getProfileImageRectangleById(long id) throws IOException {
        UserPhotoEntity image = userPhotoRepository.findById(id).get();

        String imagePath = image.getPathPhotoRectangle();

        return Files.readAllBytes(Paths.get(imagePath));
    }

}
