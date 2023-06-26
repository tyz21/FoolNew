package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userDetails.repository.AppUserRepository;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepositoryDemo;

import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserPhotoServiceDemo {

    private final AppUserRepository appUserRepository;
    private final ProfileUserRepository profileUserRepository;
    private final UserPhotoRepositoryDemo userPhotoRepository;

    public void savePhoto(AppUser appUser, MultipartFile circle, MultipartFile rectangle) throws IOException {

        appUser = appUserRepository.findById(appUser.getId()).orElse(appUser);
        ProfileUserEntity profileUserEntity = profileUserRepository.findById(appUser.getId()).get();
        byte[] circleBytes = circle.getBytes();
        byte[] rectangleBytes = rectangle.getBytes();
        UserPhotoEntityDemo photo = new UserPhotoEntityDemo(appUser, profileUserEntity);
        photo.setPhotoCircle(circleBytes);
        photo.setPhotoRectangle(rectangleBytes);

        profileUserEntity.setRatingUser(0.0);

        Long maxId = profileUserRepository.getMaxId();
        profileUserEntity.setTopUser(maxId);

        userPhotoRepository.save(photo);

        List<Long> allId = profileUserEntity.getAllIdPhotoUser();
        allId.add(photo.getId());
        profileUserEntity.setAllIdPhotoUser(allId);
        userPhotoRepository.save(photo);
    }

    public UserPhotoEntityDemo getPhotoById(Long photoId) {
        return userPhotoRepository.findById(photoId).orElse(null);
    }
}
