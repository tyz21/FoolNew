package com.example.toxicapplication.appUser.userPhoto.service;

import com.example.toxicapplication.appUser.userDetails.AppUser;
import com.example.toxicapplication.appUser.userDetails.AppUserRepository;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
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
    private final AppUserRepository appUserRepository;

    @Transactional(readOnly = true)
    public List<Long> getAllIdImage(long profileId) throws NoPhotoForProfileException {
        ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileId).get();
        if (profileUserEntity.getAllIdPhotoUser().size() != 0){
            return profileUserEntity.getAllIdPhotoUser();
        }
       throw new NoPhotoForProfileException("this profile without photo"); // so bad
    }

    @Transactional(readOnly = true)
    public long getIdProfileUser(Long idUser) {
        ProfileUserEntity profileUser = profileUserRepository.findByAppUser_Id(idUser);
        return profileUser.getId();
    }
}
