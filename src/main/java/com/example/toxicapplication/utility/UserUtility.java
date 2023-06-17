package com.example.toxicapplication.utility;

import com.example.toxicapplication.appUser.userDetails.AppUser;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class UserUtility {

    public static HttpHeaders getHttpHeaders(byte[] imageBytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);
        return headers;
    }

    public static void checkNull(Object profileUser) {
        if (profileUser == null) {
            log.info("user not found");
        }
    }

    public static void saveImageForProfile(AppUser appUser, UserPhotoEntity userPhotoEntity) {
        ProfileUserEntity profileUserEntity = appUser.getProfileUserEntity();
        if (profileUserEntity == null) {
            profileUserEntity = new ProfileUserEntity(appUser);
            appUser.setProfileUserEntity(profileUserEntity);
        }
        profileUserEntity.setUserPhotoEntity(userPhotoEntity);
        List<Long> photoIdRectangle = profileUserEntity.getAllIdRectanglePhotoUser();
        List<Long> photoIdCircle = profileUserEntity.getAllIdCirclePhotoUser();
        if (userPhotoEntity.getPathPhotoCircle() == null) {
            photoIdRectangle.add(userPhotoEntity.getId());
            profileUserEntity.setAllIdRectanglePhotoUser(photoIdRectangle);
        } else {
            photoIdCircle.add(userPhotoEntity.getId());
            profileUserEntity.setAllIdCirclePhotoUser(photoIdCircle);
        }
    }
}
