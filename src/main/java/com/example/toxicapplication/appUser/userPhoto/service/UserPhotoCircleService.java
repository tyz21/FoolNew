package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userDetails.AppUser;
import com.example.toxicapplication.appUser.userDetails.AppUserRepository;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.example.toxicapplication.utility.UserUtility.folderCreate;

@Service
@Slf4j
@AllArgsConstructor
public class UserPhotoCircleService {
    private final UserPhotoRepository userPhotoRepository;
    private final AppUserRepository appUserRepository;

    @Transactional
    public String saveImageCircle(AppUser appUser, byte[] imageBytes, String imageName) throws IOException {
        String userFolder = "images/" + appUser.getId() + "/";
        String circleFolder = userFolder + "circle/";
        String imagePath = circleFolder + imageName;

        folderCreate(imageBytes, circleFolder, imagePath);

        appUser = appUserRepository.findById(appUser.getId()).orElse(appUser);

        UserPhotoEntity userPhotoEntity = new UserPhotoEntity(appUser, imagePath);

        userPhotoRepository.save(userPhotoEntity);

        ProfileUserEntity profileUserEntity = appUser.getProfileUserEntity();
        if (profileUserEntity == null) {
            profileUserEntity = new ProfileUserEntity(appUser);
            appUser.setProfileUserEntity(profileUserEntity);
        }

        List<Long> photoIds = profileUserEntity.getAllIdPhotoUser();
        photoIds.add(userPhotoEntity.getId());
        profileUserEntity.setAllIdPhotoUser(photoIds);

        profileUserEntity.setLastIdAddPhoto(userPhotoEntity.getId());
        return imagePath;
    }

    @Transactional(readOnly = true)
    public byte[] getProfileImageCircleById(Long id) throws IOException {
        Optional<UserPhotoEntity> optionalImage = userPhotoRepository.findById(id);
        if (optionalImage.isEmpty()) {
            return null;
        }

        UserPhotoEntity profileImage = optionalImage.get();
        String imagePath = profileImage.getPathPhotoRectangle();

        return Files.readAllBytes(Paths.get(imagePath));
    }

}
