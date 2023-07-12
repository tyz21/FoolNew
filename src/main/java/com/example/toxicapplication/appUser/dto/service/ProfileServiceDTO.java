package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.ProfileUserDTO;
import com.example.toxicapplication.appUser.dto.UserPhotoDTO;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProfileServiceDTO {
    private final ProfileUserRepository profileUserRepository;
    private final TransactionTemplate transactionTemplate;

    public ProfileUserDTO getProfileUserDTO(Long profileUserId) {
        return transactionTemplate.execute(status -> {
            ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileUserId)
                    .orElseThrow(() -> new EntityNotFoundException("Profile user not found"));

            ProfileUserDTO profileUserDTO = new ProfileUserDTO();
            profileUserDTO.setIdProfile(profileUserEntity.getId());
            profileUserDTO.setProfileName(profileUserEntity.getProfileName());
            profileUserDTO.setRatingUser(profileUserEntity.getRatingUser());
            profileUserDTO.setTopUser(profileUserEntity.getTopUser());
            if (profileUserEntity.getUserPhotos().size() != 0) {
                System.out.println(profileUserEntity.getUserPhotos().size());
                List<UserPhotoEntity> userPhotos = profileUserEntity.getUserPhotos();
                userPhotos.sort(Comparator.comparing(UserPhotoEntity::getDateCreated).reversed());

                List<UserPhotoDTO> userPhotosDTO = new ArrayList<>();
                UserPhotoEntity lastCirclePhoto = Collections.max(userPhotos, Comparator.comparing(UserPhotoEntity::getDateCreated));
                profileUserDTO.setIdPhotoCircle(lastCirclePhoto.getId());
                profileUserDTO.setLastCirclePhoto(lastCirclePhoto.getPhotoCircle());

                for (UserPhotoEntity userPhotoEntity : userPhotos) {
                    UserPhotoDTO userPhotoDTO = new UserPhotoDTO();
                    userPhotoDTO.setIdPhoto(userPhotoEntity.getId());
                    userPhotoDTO.setPhotoRectangle(userPhotoEntity.getPhotoRectangle());
                    userPhotoDTO.setCountMark(userPhotoEntity.getCountMark());
                    userPhotoDTO.setSumMark(userPhotoEntity.getSumMark());
                    userPhotoDTO.setRatingPhoto(userPhotoEntity.getRatingPhoto());
                    userPhotoDTO.setTopPhoto(userPhotoEntity.getTopPhoto());
                    userPhotoDTO.setDateCreated(userPhotoEntity.getDateCreated().toString());
                    userPhotosDTO.add(userPhotoDTO);
                }

                profileUserDTO.setUserPhotos(userPhotosDTO);

                return profileUserDTO;
            }
            else {
                profileUserDTO.setUserPhotos(new ArrayList<>());
                return profileUserDTO;
            }
        });
    }
}
