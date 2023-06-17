package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.toxicapplication.utility.UserUtility.checkNull;

@Service
@Slf4j
@AllArgsConstructor
public class UserRatingService {
    private final UserPhotoRepository userPhotoRepository;
    private final ProfileUserRepository profileUserRepository;

    @Transactional
    public double postRating(Long photoId, double rating) {
        UserPhotoEntity userPhotoEntity = userPhotoRepository.findById(photoId).get();
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

        ProfileUserEntity profileUserEntity = userPhotoEntity.getProfileUserEntity();
        checkNull(profileUserEntity);

        setRatingForProfile(profileUserEntity.getId());
        setTopUserForProfileAndPhoto(profileUserEntity.getId());

        return userPhotoEntity.getRatingPhoto();
    }

    public void setRatingForProfile(Long id) {
        ProfileUserEntity profileUser = profileUserRepository.findById(id).orElse(null);
        checkNull(profileUser);
        Long lastIdAddPhoto = profileUser.getAllIdRectanglePhotoUser().get(profileUser.getAllIdRectanglePhotoUser().size() - 1);

        UserPhotoEntity userPhotoEntity = userPhotoRepository.findById(lastIdAddPhoto).orElse(null);
        checkNull(userPhotoEntity);

        double ratingPhoto = Objects.requireNonNull(userPhotoEntity).getRatingPhoto();
        profileUser.setRatingUser(ratingPhoto);
        profileUserRepository.save(profileUser);

    }

    public void setTopUserForProfileAndPhoto(Long id) {
        ProfileUserEntity profileUser = profileUserRepository.findById(id).orElse(null);
        checkNull(profileUser);

        List<ProfileUserEntity> allProfiles = profileUserRepository.findAll();

        allProfiles.sort(Comparator.comparingDouble(ProfileUserEntity::getRatingUser).reversed());

        makeTopFromRating(allProfiles);
    }

    private void makeTopFromRating(List<ProfileUserEntity> allProfiles) {
        for (int i = 0; i < allProfiles.size(); i++) {
            ProfileUserEntity profile = allProfiles.get(i);
            profile.setTopUser(i + 1);
            Long lastIdAddPhoto = profile.getAllIdRectanglePhotoUser().get(profile.getAllIdRectanglePhotoUser().size() - 1);
            if (lastIdAddPhoto == 0) {
                continue;
            }
            UserPhotoEntity userPhoto = userPhotoRepository.findById(lastIdAddPhoto).get();
            userPhoto.setTopPhoto(i + 1L);

            profileUserRepository.save(profile);
            userPhotoRepository.save(userPhoto);
        }
    }

    @Transactional(readOnly = true)
    public double getRatingPhoto(Long profileId) {
        UserPhotoEntity userPhotoEntity = userPhotoRepository.findById(profileId).get();
        return userPhotoEntity.getRatingPhoto();
    }
}