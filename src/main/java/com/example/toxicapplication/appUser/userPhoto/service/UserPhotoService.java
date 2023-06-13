package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import com.example.toxicapplication.exception.NoPhotoForProfileException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserPhotoService {
    private final ProfileUserRepository profileUserRepository;

    @Transactional(readOnly = true)
    public List<Long> getAllIdCircleImage(long profileId) throws NoPhotoForProfileException {
        ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileId).get();
        if (profileUserEntity.getAllIdCirclePhotoUser().size() != 0){
            return profileUserEntity.getAllIdCirclePhotoUser();
        }
        throw new NoPhotoForProfileException("this profile without photo"); // so bad
    }
    @Transactional(readOnly = true)
    public List<Long> getAllIdRectangleImage(long profileId) throws NoPhotoForProfileException {
        ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileId).get();
        if (profileUserEntity.getAllIdRectanglePhotoUser().size() != 0){
            return profileUserEntity.getAllIdRectanglePhotoUser();
        }
        throw new NoPhotoForProfileException("this profile without photo");
    }
    @Transactional
    public long provideRandomIdPhotoUser() throws NoPhotoForProfileException {
        long maxId = profileUserRepository.getMaxId();
        long randomId = (long) (Math.random() * maxId) + 1L;
        ProfileUserEntity randomUser = profileUserRepository.findById(randomId).get();
        if (randomUser.getUserPhotoEntity() == null) {
            return provideRandomIdPhotoUser();
        }
        boolean isEmpty = randomUser.getAllIdRectanglePhotoUser().isEmpty();
        if (!isEmpty) {
            return randomUser.getAllIdRectanglePhotoUser().get(randomUser.getAllIdRectanglePhotoUser().size() - 1);

        } else {
            throw new NoPhotoForProfileException("no photo for this profile"); // so bad
        }
    }
}

