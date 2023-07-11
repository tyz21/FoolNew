package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.ProfileUserDTO;
import com.example.toxicapplication.appUser.dto.UserPhotoDTO;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
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
public class DTOProfileService {
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

            List<UserPhotoEntityDemo> userPhotos = profileUserEntity.getUserPhotos();
            userPhotos.sort(Comparator.comparing(UserPhotoEntityDemo::getDateCreated).reversed());

            List<UserPhotoDTO> userPhotosDTO = new ArrayList<>();
            UserPhotoEntityDemo lastCirclePhoto = Collections.max(userPhotos, Comparator.comparing(UserPhotoEntityDemo::getDateCreated));
            profileUserDTO.setIdPhotoCircle(lastCirclePhoto.getId());
            profileUserDTO.setLastCirclePhoto(lastCirclePhoto.getPhotoCircle());

            for (UserPhotoEntityDemo userPhotoEntity : userPhotos) {
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
        });
    }
}
