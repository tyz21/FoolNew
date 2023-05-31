package com.example.toxicapplication.appUser.userProfile;

import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProfileUserService {
    private final UserPhotoRepository userPhotoRepository;
    private final ProfileUserRepository profileUserRepository;

    public double getRating(Long id){
        ProfileUserEntity profileUser = profileUserRepository.findById(id).get();
        return profileUser.getRatingUser();
    }
    public List<Long> getAllPhotoIDs(Long profileId) {
        ProfileUserEntity profileUserEntity = profileUserRepository.findAllById(profileId);
        return profileUserEntity.getAllIdPhotoUser();
    }

    public ProfileUserEntity getProfile(Long profileId) {
        return profileUserRepository.findProfileUserEntityById(profileId);
    }
}
