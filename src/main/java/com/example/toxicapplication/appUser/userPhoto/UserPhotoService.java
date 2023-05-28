package com.example.toxicapplication.appUser.userPhoto;

import com.example.toxicapplication.appUser.userDetails.AppUser;
import com.example.toxicapplication.appUser.userDetails.AppUserRepository;
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

    @Transactional
    public String saveImageRectangle(AppUser appUser, byte[] imageBytes, String imageName) throws IOException {
        String userFolder = "images/" + appUser.getId() + "/";
        String rectangleFolder = userFolder + "rectangle/";
        String imagePath = rectangleFolder + imageName;

        File folder = new File(rectangleFolder);
        if (!folder.exists()) {
            folder.mkdirs();
         //   Files.createDirectories(Paths.get("images", appUser.getId().toString()));
        }

        Files.write(Paths.get(imagePath), imageBytes);

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

        return imagePath;
    }

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
