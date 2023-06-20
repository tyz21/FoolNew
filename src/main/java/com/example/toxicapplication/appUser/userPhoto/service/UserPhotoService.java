package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import com.example.toxicapplication.exception.NoPhotoForProfileException;
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

    @Transactional
    public List<ProfileUserEntity> provideRandomUsers() throws NoPhotoForProfileException {
        List<ProfileUserEntity> randomUsers = new ArrayList<>();
        long maxId = profileUserRepository.getMaxId();

        while (randomUsers.size() < 20) {
            long randomId = (long) (Math.random() * maxId) + 1L;
            ProfileUserEntity randomUser = profileUserRepository.findById(randomId).get();

            if (randomUser.getUserPhotoEntity() != null && !randomUser.getAllIdRectanglePhotoUser().isEmpty()) {
                randomUsers.add(randomUser);
            }
        }

        if (randomUsers.isEmpty()) {
            throw new NoPhotoForProfileException("No photo for any profile");
        }

        return randomUsers;
    }
}

