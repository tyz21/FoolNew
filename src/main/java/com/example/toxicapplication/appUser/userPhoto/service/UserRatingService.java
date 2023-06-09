package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import com.example.toxicapplication.exception.NoPhotoForProfileException;
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
    public double postRating(Long profileId, double rating) throws NoPhotoForProfileException {

        Optional<UserPhotoEntity> optionalImage = userPhotoRepository.findById(profileId);
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
        ProfileUserEntity profileUser = profileUserRepository.findProfileUserEntityByLastIdAddPhoto(profileId);
        if (profileUser == null) {
            throw new NoPhotoForProfileException("no photo for this profile");
        }
//        if (profileUser.getId() == null) {
//            return 0.0;
//        }
        setRatingForProfile(profileUser.getId());
        setTopUserForProfileAndPhoto(profileUser.getId());

        return userPhotoEntity.getRatingPhoto();
    }

    @Transactional
    public long provideRandomIdPhotoUser() throws NoPhotoForProfileException {
        long maxId = profileUserRepository.getMaxId();
        long randomId = (long) (Math.random() * maxId) + 1L;
        ProfileUserEntity randomUser = profileUserRepository.findById(randomId).get();
        if(randomUser.getLastIdAddPhoto() != 0){
            return randomUser.getLastIdAddPhoto();
        } else {
            throw new NoPhotoForProfileException("no photo for this profile"); // so bad
        }
    }

    public void setRatingForProfile(Long id) {
        ProfileUserEntity profileUser = profileUserRepository.findById(id).orElse(null);
        checkNull(profileUser);

        UserPhotoEntity userPhotoEntity = userPhotoRepository.findById(profileUser.getLastIdAddPhoto()).orElse(null);
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

            if (profile.getLastIdAddPhoto() == 0) {
                continue;
            }
            UserPhotoEntity userPhoto = userPhotoRepository.findById(profile.getLastIdAddPhoto()).get();
            userPhoto.setTopPhoto(i + 1L);

            profileUserRepository.save(profile);
            userPhotoRepository.save(userPhoto);

        }
    }

}