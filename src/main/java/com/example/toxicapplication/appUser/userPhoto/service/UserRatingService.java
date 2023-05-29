package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserRatingService {
    private final UserPhotoRepository userPhotoRepository;
    private final ProfileUserRepository profileUserRepository;


    @Transactional(readOnly = true)
    public double postRating(Long profileId, double rating) {
        ProfileUserEntity profileUser = profileUserRepository.findById(profileId).get();
        Optional<UserPhotoEntity> optionalImage = userPhotoRepository.findById(profileUser.getLastIdAddPhoto());
        if (optionalImage.isEmpty()) {
            return 0.0; // Nothing to return
        }
        UserPhotoEntity userPhotoEntity = optionalImage.get();
        double currentRating = userPhotoEntity.getRatingPhoto();
        if (currentRating == 0) {
            userPhotoEntity.setRatingPhoto(rating);
        } else {
            double newRatingPhoto = (currentRating + rating) / 2.0;
            userPhotoEntity.setRatingPhoto(newRatingPhoto);
        }
        setRatingForProfile(profileId);
        userPhotoRepository.save(userPhotoEntity);
        return userPhotoEntity.getRatingPhoto();
    }

    public void setRatingForProfile(Long id) { // for current user
        ProfileUserEntity profileUser = profileUserRepository.findById(id).get();
        UserPhotoEntity userPhotoEntity = userPhotoRepository.findById(profileUser.getLastIdAddPhoto()).get();
        double ratingPhoto = userPhotoEntity.getRatingPhoto();
        profileUser.setRatingUser(ratingPhoto);
    }

    public long provideRandomId() {
        long maxId = profileUserRepository.getMaxId();
        return (long) (Math.random() * maxId) + 1; // don't include 0
    }
}
