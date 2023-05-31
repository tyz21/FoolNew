package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserRatingService {
    private final UserPhotoRepository userPhotoRepository;
    private final ProfileUserRepository profileUserRepository;

    @Transactional
    public double postRating(Long profileId, double rating) {
        ProfileUserEntity profileUser = profileUserRepository.findById(profileId).orElse(null);
        if (profileUser == null) {
            return 0.0; // Profile not found
        }

        Optional<UserPhotoEntity> optionalImage = userPhotoRepository.findById(profileUser.getLastIdAddPhoto());
        if (optionalImage.isEmpty()) {
            return 0.0; // No photo found
        }
        UserPhotoEntity userPhotoEntity = optionalImage.get();

        double currentRating = userPhotoEntity.getRatingPhoto();
        if (currentRating == 0) {
            userPhotoEntity.setRatingPhoto(rating);
        } else {
            double newRatingPhoto = (currentRating + rating) / 2.0;
            userPhotoEntity.setRatingPhoto(newRatingPhoto);
        }
        userPhotoRepository.save(userPhotoEntity);

        setRatingForProfile(profileId);
        setTopUserForProfile(profileId);

        return userPhotoEntity.getRatingPhoto();
    }

    private void setRatingForProfile(Long id) {
        ProfileUserEntity profileUser = profileUserRepository.findById(id).orElse(null);
        if (profileUser == null) {
            return;
        }

        UserPhotoEntity userPhotoEntity = userPhotoRepository.findById(profileUser.getLastIdAddPhoto()).orElse(null);
        if (userPhotoEntity == null) {
            return;
        }

        double ratingPhoto = userPhotoEntity.getRatingPhoto();
        profileUser.setRatingUser(ratingPhoto);
        profileUserRepository.save(profileUser);
    }

    private void setTopUserForProfile(Long id) {
        ProfileUserEntity profileUser = profileUserRepository.findById(id).orElse(null);
        if (profileUser == null) {
            return;
        }

        List<ProfileUserEntity> allProfiles = profileUserRepository.findAll();

        allProfiles.sort(Comparator.comparingDouble(ProfileUserEntity::getRatingUser).reversed());

        for (int i = 0; i < allProfiles.size(); i++) {
            ProfileUserEntity profile = allProfiles.get(i);
            profile.setTopUser(i + 1);
            profileUserRepository.save(profile);
        }
    }

        public long provideRandomIdPhotoUser() {
        long maxId = profileUserRepository.getMaxId();
        long randomId = (long) (Math.random() * maxId) + 1L;
        ProfileUserEntity randomUser = profileUserRepository.findById(randomId).get();
        return randomUser.getLastIdAddPhoto(); // don't include 0
    }
}