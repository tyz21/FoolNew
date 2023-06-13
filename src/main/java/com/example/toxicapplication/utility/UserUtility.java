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
    public static String pathPhoto(byte[] imageBytes, String imageName, String folderName) throws IOException {
        String imagePath = folderName + imageName;

        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdirs();
            //   Files.createDirectories(Paths.get("images", appUser.getId().toString()));
        }

        Files.write(Paths.get(imagePath), imageBytes);
        return imagePath;
    }

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

        List<Long> photoIds = profileUserEntity.getAllIdRectanglePhotoUser();
        photoIds.add(userPhotoEntity.getId());
//        if (userPhotoEntity.getPathPhotoCircle() == null) {
//            profileUserEntity.setAllIdRectanglePhotoUser(photoIds);
//        } else {
//            profileUserEntity.setAllIdCirclePhotoUser(photoIds);
//        } // refactor now
    }
}
