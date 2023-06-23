package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.ProfileUserDTO;
import com.example.toxicapplication.appUser.dto.UserPhotoDTO;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DTOUserService {
    private final ProfileUserRepository profileUserRepository;
    private final TransactionTemplate transactionTemplate;

    public ProfileUserDTO getProfileUserDTO(Long profileUserId) {
        return transactionTemplate.execute(status -> {
            ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileUserId)
                    .orElseThrow(() -> new EntityNotFoundException("Profile user not found"));

            ProfileUserDTO profileUserDTO = new ProfileUserDTO();
            profileUserDTO.setAppUser(profileUserEntity.getAppUser());
            profileUserDTO.setProfileName(profileUserEntity.getProfileName());
            profileUserDTO.setRatingUser(profileUserEntity.getRatingUser());
            profileUserDTO.setTopUser(profileUserEntity.getTopUser());

            List<UserPhotoDTO> userPhotosDTO = new ArrayList<>();
            for (UserPhotoEntityDemo userPhotoEntity : profileUserEntity.getUserPhotos()) {
                UserPhotoDTO userPhotoDTO = new UserPhotoDTO();
                userPhotoDTO.setPhotoRectangle(userPhotoEntity.getPhotoRectangle());
                userPhotoDTO.setPhotoCircle(userPhotoEntity.getPhotoCircle());
                userPhotoDTO.setCountMark(userPhotoEntity.getCountMark());
                userPhotoDTO.setSumMark(userPhotoEntity.getSumMark());
                userPhotoDTO.setRatingPhoto(userPhotoEntity.getRatingPhoto());
                userPhotoDTO.setTopPhoto(userPhotoEntity.getTopPhoto());
                userPhotosDTO.add(userPhotoDTO);
            }
            profileUserDTO.setUserPhotos(userPhotosDTO);

            return profileUserDTO;
        });
    }
}
