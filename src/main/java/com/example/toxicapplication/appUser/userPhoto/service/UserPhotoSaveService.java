package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userDetails.repository.AppUserRepository;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.toxicapplication.utility.UserUtility.saveImageForProfile;

@Service
@Slf4j
@AllArgsConstructor
public class UserPhotoSaveService {
    private final UserPhotoRepository userPhotoRepository;
    private final AppUserRepository appUserRepository;
    private final ProfileUserRepository profileUserRepository;

    @Transactional
    public String saveImageRectangle(AppUser appUser, byte[] imageBytes, String imageName) throws IOException {
        String imagePath = savePhoto(imageBytes, imageName);
        appUser = appUserRepository.findById(appUser.getId()).orElse(appUser);
        ProfileUserEntity profileUserEntity = profileUserRepository.findById(appUser.getId()).get();
        UserPhotoEntity userPhotoEntity = new UserPhotoEntity(appUser);
        if (imagePath.contains("/rectangle")) {
            userPhotoEntity.setPathPhotoRectangle(imagePath);
        } else {
            userPhotoEntity.setPathPhotoCircle(imagePath);
        }
        userPhotoEntity.setProfileUserEntity(appUser.getProfileUserEntity());
        profileUserEntity.setRatingUser(0.0);
        profileUserEntity.setTopUser(0);
        userPhotoRepository.save(userPhotoEntity);

        saveImageForProfile(appUser, userPhotoEntity);

        return imagePath;
    }

    private static String savePhoto(byte[] imageBytes, String imageName) throws IOException {
        String userFolder = "images/";
        String imagePath = userFolder + imageName;

        File folder = new File(userFolder);
        if (!folder.exists()) {
            folder.mkdirs();
            //   Files.createDirectories(Paths.get("images", appUser.getId().toString()));
        }

        Files.write(Paths.get(imagePath), imageBytes);
        return imagePath;
    }

    @Transactional(readOnly = true)
    public byte[] getProfileImageById(long id) throws IOException {
        UserPhotoEntity image = userPhotoRepository.findById(id).get();
        if (image.getPathPhotoRectangle() == null) {
            return Files.readAllBytes(Paths.get(image.getPathPhotoCircle()));
        }
        return Files.readAllBytes(Paths.get(image.getPathPhotoRectangle()));
    }

}
