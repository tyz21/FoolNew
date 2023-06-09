package com.example.toxicapplication.appUser.userProfile;

import com.example.toxicapplication.appUser.userDetails.AppUser;
import com.example.toxicapplication.appUser.userDetails.AppUserRepository;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.reposirory.UserPhotoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProfileUserService {
    private final ProfileUserRepository profileUserRepository;
    private final UserPhotoRepository userPhotoRepository;
    private final AppUserRepository appUserRepository;

    public long getProfileId(Long id){
        ProfileUserEntity profileUser = profileUserRepository.findByAppUser_Id(id);
        return profileUser.getId();
    }
    public long getTop(Long profileId) {
        UserPhotoEntity userPhotoEntity = userPhotoRepository.findById(profileId).get();
        return userPhotoEntity.getTopPhoto();
    }

    public double getRatingPhoto(Long profileId) {
        UserPhotoEntity userPhotoEntity = userPhotoRepository.findById(profileId).get();
        return userPhotoEntity.getRatingPhoto();
    }

    public List<Long> getAllPhotoIDs(Long profileId) {
        ProfileUserEntity profileUserEntity = profileUserRepository.findAllById(profileId);
        return profileUserEntity.getAllIdPhotoUser();
    }
    public String getUserName(Long profileId) {
        ProfileUserEntity profileUser = profileUserRepository.findById(profileId).get();
        AppUser appUser = appUserRepository.findById(profileUser.getId()).get();
        return appUser.getUsername();
    }

    public String getAllUser() {
        StringBuilder users = new StringBuilder();
        Pageable pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "topUser"));
        Page<ProfileUserEntity> page = profileUserRepository.findAll(pageable);
        List<ProfileUserEntity> profileUser = page.getContent();

        for (int i = 0; i < profileUser.size(); i++) {
            ProfileUserEntity user = profileUser.get(i);
            users.append(user.getId());

            if (i < profileUser.size() - 1) {
                users.append(", ");
            }
        }

        return users.toString();
    }
}
