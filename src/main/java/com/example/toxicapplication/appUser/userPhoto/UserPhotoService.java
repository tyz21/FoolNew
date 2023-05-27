package com.example.toxicapplication.appUser.userPhoto;

import com.example.toxicapplication.appUser.AppUser;
import com.example.toxicapplication.appUser.AppUserRepository;
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

@Service
@Slf4j
@AllArgsConstructor
public class UserPhotoService {
    private final UserPhotoRepository userPhotoRepository;
    private final AppUserRepository appUserRepository;

    @Transactional(readOnly = true)
    public List<Long> getProfileImageIdsByUserId(Long userId) {
        return userPhotoRepository.findImageIdsByUserId(userId);
    }
    @Transactional
    public String saveImageRectangle(AppUser appUser, byte[] imageBytes, String imageName) throws IOException {
        String userFolder = "images/" + appUser.getId() + "/";
        String rectangleFolder = userFolder + "rectangle/";
        String imagePath = rectangleFolder + imageName;

        File folder = new File(rectangleFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Files.write(Paths.get(imagePath), imageBytes);

        // Fetch the AppUser entity from the database if it exists
        appUser = appUserRepository.findById(appUser.getId()).orElse(appUser);

        UserPhotoEntity userPhotoEntity = new UserPhotoEntity(appUser, imagePath);
        userPhotoRepository.save(userPhotoEntity);

        // Update ProfileUserEntity
        ProfileUserEntity profileUserEntity = appUser.getProfileUserEntity();
        if (profileUserEntity == null) {
            profileUserEntity = new ProfileUserEntity(appUser);
            appUser.setProfileUserEntity(profileUserEntity);
        }
        List<Long> photoIds = profileUserEntity.getAllIdPhotoUser();
        photoIds.add(userPhotoEntity.getId());
        profileUserEntity.setAllIdPhotoUser(photoIds);

        return imagePath;
    }
//    public String saveImageRectangle(AppUser appUser, byte[] imageBytes, String imageName) throws IOException {
//        String userFolder = "images/" + appUser.getId() + "/";
//        String rectangleFolder = userFolder + "rectangle/";
//        String imagePath = rectangleFolder + imageName;
//
//        File folder = new File(rectangleFolder);
//        if (!folder.exists()) {
//            folder.mkdirs();
//        }
//
//        Files.write(Paths.get(imagePath), imageBytes);
//        UserPhotoEntity userPhotoEntity = new UserPhotoEntity(appUser, imagePath);
//        userPhotoRepository.save(userPhotoEntity);
//        return imagePath;
//    }
//@Transactional
//public String saveImageRectangle(AppUser appUser, byte[] imageBytes, String imageName) throws IOException {
//    String userFolder = "images/" + appUser.getId() + "/";
//    String rectangleFolder = userFolder + "rectangle/";
//    String imagePath = rectangleFolder + imageName;
//
//    File folder = new File(rectangleFolder);
//    if (!folder.exists()) {
//        folder.mkdirs();
//    }
//
//    Files.write(Paths.get(imagePath), imageBytes);
//
//    // Fetch the AppUser entity from the database if it exists
//    appUser = appUserRepository.findById(appUser.getId()).orElse(appUser);
//
//    UserPhotoEntity userPhotoEntity = new UserPhotoEntity(appUser, imagePath);
//    userPhotoRepository.save(userPhotoEntity);
//    return imagePath;
//}
    //    public String saveImageCircle(AppUser appUser, byte[] imageBytes, String imageName) throws IOException {
//        String userFolder = "images/" + appUser.getId() + "/";
//        String imagePath = userFolder + imageName;
//
//        // Create the user's folder if it doesn't exist
//        File folder = new File(userFolder);
//        if (!folder.exists()) {
//            //Files.createDirectories(Paths.get("images", appUser.getId().toString()));
//            folder.mkdirs();
//        }
//
//        Files.write(Paths.get(imagePath), imageBytes);
//        ProfileUserEntity profileUserEntity = new ProfileUserEntity(appUser, imagePath);
//        userPhotoRepository.save(profileUserEntity);
//        return imagePath;
//    }
    @Transactional(readOnly = true)
    public byte[] getProfileImageReqtangleById(Long id) throws IOException {
        Optional<UserPhotoEntity> optionalImage = userPhotoRepository.findById(id);
        if (optionalImage.isEmpty()) {
            return null;
        }

        UserPhotoEntity profileImage = optionalImage.get();
        String imagePath = profileImage.getPathPhotoRectangle();

        return Files.readAllBytes(Paths.get(imagePath));
    }
}
