package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.SubscribeDTO;
import com.example.toxicapplication.appUser.subscribe.Subscribe;
import com.example.toxicapplication.appUser.subscribe.SubscribeRepository;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@AllArgsConstructor
@Service
public class SubscribeServiceDTO {
    private final TransactionTemplate transactionTemplate;
    private final SubscribeRepository subscribeRepository;
    public List<SubscribeDTO> getSubscriber(Long idUser) {
        return transactionTemplate.execute(status -> {
            List<ProfileUserEntity> profileUserEntities = new ArrayList<>();
            List<Subscribe> subscribes = subscribeRepository.findAll();
            for (Subscribe subscribe : subscribes) {
                if (subscribe.getAppUser() != null && subscribe.getProfileUserEntity() != null) {
                    if (subscribe.getAppUser().getId().equals(idUser)) {
                        ProfileUserEntity profileUser = subscribe.getProfileUserEntity();
                        if (!profileUserEntities.contains(profileUser)) {
                            profileUserEntities.add(profileUser);
                        }
                    }
                }
            }
            List<SubscribeDTO> subscribeDTOS = new ArrayList<>();
            for (ProfileUserEntity profileUser : profileUserEntities) {
                SubscribeDTO subscribeDTO = new SubscribeDTO();
                subscribeDTO.setIdProfile(profileUser.getId());
                subscribeDTO.setProfileName(profileUser.getProfileName());

                List<UserPhotoEntityDemo> userPhotos = profileUser.getUserPhotos();
                userPhotos.sort(Comparator.comparing(UserPhotoEntityDemo::getDateCreated).reversed());
                UserPhotoEntityDemo lastCirclePhoto = null;
                if (!userPhotos.isEmpty()) {
                    lastCirclePhoto = Collections.max(userPhotos, Comparator.comparing(UserPhotoEntityDemo::getDateCreated));
                    subscribeDTO.setIdPhotoCircle(lastCirclePhoto.getId());
                    subscribeDTO.setLastCirclePhoto(lastCirclePhoto.getPhotoCircle());
                }
                subscribeDTOS.add(subscribeDTO);
            }
            return subscribeDTOS;
        });
    }
}
//    private SubscribeDTO convertToSubscribeDTO(ProfileUserEntity profileUserEntity) {
//        SubscribeDTO subscribeDTO = new SubscribeDTO();
//        subscribeDTO.setIdProfile(profileUserEntity.getId());
//        subscribeDTO.setProfileName(profileUserEntity.getProfileName());
//        List<UserPhotoEntityDemo> userPhotos = profileUserEntity.getUserPhotos();
//        if (!userPhotos.isEmpty()) {
//            UserPhotoEntityDemo lastCirclePhoto = Collections.max(userPhotos, Comparator.comparing(UserPhotoEntityDemo::getDateCreated));
//            subscribeDTO.setIdPhotoCircle(lastCirclePhoto.getId());
//            subscribeDTO.setLastCirclePhoto(lastCirclePhoto.getPhotoCircle());
//        }
//        return subscribeDTO;
//    }
//}