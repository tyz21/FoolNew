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
public class DTOProfileService {
    private final ProfileUserRepository profileUserRepository;
    private final TransactionTemplate transactionTemplate;
//    public ProfileUserDTO getProfileUserDTO(Long profileUserId) {
//        return transactionTemplate.execute(status -> {
//            ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileUserId)
//                    .orElseThrow(() -> new EntityNotFoundException("Profile user not found"));
//
//            ProfileUserDTO profileUserDTO = new ProfileUserDTO();
//            profileUserDTO.setProfileName(profileUserEntity.getProfileName());
//            profileUserDTO.setRatingUser(profileUserEntity.getRatingUser());
//            profileUserDTO.setTopUser(profileUserEntity.getTopUser());
//
//            List<UserPhotoEntityDemo> userPhotos = profileUserEntity.getUserPhotos();
//            if (!userPhotos.isEmpty()) {
//                UserPhotoEntityDemo lastUserPhoto = userPhotos.get(userPhotos.size() - 1);
//
//                List<UserPhotoDTO> userPhotosDTO = new ArrayList<>();
//                UserPhotoDTO lastUserPhotoDTO = new UserPhotoDTO();
//                //lastUserPhotoDTO.setPhotoRectangle(lastUserPhoto.getPhotoRectangle());
//               // lastUserPhotoDTO.setCountMark(lastUserPhoto.getCountMark());
//               // lastUserPhotoDTO.setSumMark(lastUserPhoto.getSumMark());
//                //lastUserPhotoDTO.setRatingPhoto(lastUserPhoto.getRatingPhoto());
//              //  lastUserPhotoDTO.setTopPhoto(lastUserPhoto.getTopPhoto());
//
//                profileUserDTO.setLastCirclePhoto(lastUserPhoto.getPhotoCircle());
//                userPhotosDTO.add(lastUserPhotoDTO);
//
//                for (UserPhotoEntityDemo userPhotoEntity : userPhotos) {
//                    if (userPhotoEntity != lastUserPhoto) {
//                        UserPhotoDTO userPhotoDTO = new UserPhotoDTO();
//                        userPhotoDTO.setPhotoRectangle(userPhotoEntity.getPhotoRectangle());
//                        userPhotoDTO.setCountMark(userPhotoEntity.getCountMark());
//                        userPhotoDTO.setSumMark(userPhotoEntity.getSumMark());
//                        userPhotoDTO.setRatingPhoto(userPhotoEntity.getRatingPhoto());
//                        userPhotoDTO.setTopPhoto(userPhotoEntity.getTopPhoto());
//                        userPhotosDTO.add(userPhotoDTO);
//                    }
//                }
//                profileUserDTO.setUserPhotos(userPhotosDTO);
//            }
//
//            return profileUserDTO;
//        });
//    }
//}
    public ProfileUserDTO getProfileUserDTO(Long profileUserId) {
        return transactionTemplate.execute(status -> {
            ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileUserId)
                    .orElseThrow(() -> new EntityNotFoundException("Profile user not found"));

            ProfileUserDTO profileUserDTO = new ProfileUserDTO();
            profileUserDTO.setProfileName(profileUserEntity.getProfileName());
            profileUserDTO.setRatingUser(profileUserEntity.getRatingUser());
            profileUserDTO.setTopUser(profileUserEntity.getTopUser());

            List<UserPhotoDTO> userPhotosDTO = new ArrayList<>();
            profileUserDTO.setLastCirclePhoto(profileUserEntity.getUserPhotos().get(profileUserEntity.getUserPhotos().size() - 1).getPhotoCircle());
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
