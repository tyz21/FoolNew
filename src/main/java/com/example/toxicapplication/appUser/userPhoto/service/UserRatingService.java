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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserRatingService {
    private final UserPhotoRepositoryDemo userPhotoRepository;
    private final ProfileUserRepository profileUserRepository;

    @Transactional
    public double postRating(Long photoId, double rating) {
        UserPhotoEntityDemo userPhotoEntity = userPhotoRepository.findById(photoId).get();

        Integer countMark = userPhotoEntity.getCountMark();
        Double sumMark = userPhotoEntity.getSumMark();
        countMark = (countMark == null) ? 0 : countMark;
        sumMark = (sumMark == null) ? 0 : sumMark;

        userPhotoEntity.setCountMark(countMark + 1);
        userPhotoEntity.setSumMark(sumMark + rating);
        double currentRating = userPhotoEntity.getRatingPhoto();
        if (currentRating == 0) {
            userPhotoEntity.setRatingPhoto(rating);
        } else {
            double newRatingPhoto = (userPhotoEntity.getSumMark() / userPhotoEntity.getCountMark());
            userPhotoEntity.setRatingPhoto(newRatingPhoto);
        }
        userPhotoRepository.save(userPhotoEntity);

        AppUser appUser = userPhotoEntity.getAppUser();
        ProfileUserEntity profileUserEntity = profileUserRepository.findById(appUser.getId()).get();

        setRatingForProfile(profileUserEntity.getId());
        setTopUserForProfileAndPhoto();

        return userPhotoEntity.getRatingPhoto();
    }

    public void setRatingForProfile(Long id) {
        ProfileUserEntity profileUser = profileUserRepository.findById(id).orElse(null);

        Long lastIdAddPhoto = profileUser.getAllIdPhotoUser().get(profileUser.getAllIdPhotoUser().size() - 1);

        UserPhotoEntityDemo userPhotoEntity = userPhotoRepository.findById(lastIdAddPhoto).orElse(null);

        double ratingPhoto = Objects.requireNonNull(userPhotoEntity).getRatingPhoto();
        profileUser.setRatingUser(ratingPhoto);
        profileUserRepository.save(profileUser);

    }

    public void setTopUserForProfileAndPhoto() {

        List<ProfileUserEntity> allProfiles = profileUserRepository.findAll();

        allProfiles.sort(Comparator.comparingDouble(ProfileUserEntity::getRatingUser).reversed());

        makeTopFromRating(allProfiles);
    }

    private void makeTopFromRating(List<ProfileUserEntity> allProfiles) {
        for (int i = 0; i < allProfiles.size(); i++) {
            ProfileUserEntity profile = allProfiles.get(i);

            profile.setTopUser(i + 1);

//            List<Long> allIdPhotoUser = profile.getAllIdPhotoUser();
//            if (allIdPhotoUser.isEmpty()) {
//                continue;
//            }
            Long lastIdAddPhoto = profile.getAllIdPhotoUser().get(profile.getAllIdPhotoUser().size() - 1);

            if (lastIdAddPhoto == 0) {
                continue;
            }
            UserPhotoEntityDemo userPhoto = userPhotoRepository.findById(lastIdAddPhoto).get();
            userPhoto.setTopPhoto(i + 1L);

            profileUserRepository.save(profile);
            userPhotoRepository.save(userPhoto);
        }
    }

}