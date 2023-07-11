package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserPhotoService {
    private final ProfileUserRepository profileUserRepository;
    private final UserPhotoRepository userPhotoRepository;

//    public List<ProfileUserEntity> provideRandomUsers() throws NoPhotoForProfileException {
//        List<ProfileUserEntity> randomUsers = new ArrayList<>();
//        ProfileUserEntity randomUser;
//        long maxId = profileUserRepository.getMaxId();
//        while (randomUsers.size() < 20) {
//            long randomId = (long) (Math.random() * maxId) + 1L;
//            randomUser = profileUserRepository.findById(randomId).get();
//
//            if (randomUser != null) {
//                randomUsers.add(randomUser);
//            }
//        }
//
//        return randomUsers;
//    }

    @Transactional
    public List<UserPhotoEntityDemo> getALlPhotoData(Long id) {
        ProfileUserEntity profileUser = profileUserRepository.findById(id).get();
        List<UserPhotoEntityDemo> allPhoto = userPhotoRepository.findAll();
        List<UserPhotoEntityDemo> allPhotoProfile = new ArrayList<>();
        for (UserPhotoEntityDemo userPhoto : allPhoto) {
            if (userPhoto.getAppUser() == profileUser.getAppUser()) {
                allPhotoProfile.add(userPhoto);
            }
        }
        return allPhotoProfile;
    }
}

