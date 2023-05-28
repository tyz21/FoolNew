package com.example.toxicapplication.appUser.userProfile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProfileUserService {
    private final ProfileUserRepository profileUserRepository;
    public List<Long> getAllPhotoIDs(Long profileId) {
        ProfileUserEntity profileUserEntity = profileUserRepository.findAllById(profileId);
        return profileUserEntity.getAllIdPhotoUser();
    }

    public ProfileUserEntity getProfile(Long profileId) {
        return profileUserRepository.findProfileUserEntityById(profileId);
    }
}
