package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.RatingDTO;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@AllArgsConstructor
@Service
@Slf4j
public class RatingServiceDTO {
    private final ProfileUserRepository profileUserRepository;
    private final TransactionTemplate transactionTemplate;

    public List<RatingDTO> getRandomRatingDTOs(long count, long idProfile) {
        return transactionTemplate.execute(status -> {
            List<RatingDTO> randomRatingDTOs = new ArrayList<>();
            List<ProfileUserEntity> randomUsers = provideRandomUsers(count, idProfile);

            for (ProfileUserEntity profileUserEntity : randomUsers) {
                RatingDTO ratingDTO = new RatingDTO();
                ratingDTO.setProfileName(profileUserEntity.getProfileName());
                ratingDTO.setRatingUser(profileUserEntity.getRatingUser());
                ratingDTO.setTopUser(profileUserEntity.getTopUser());
                ratingDTO.setIdProfile(profileUserEntity.getId());
                List<UserPhotoEntity> userPhotos = profileUserEntity.getUserPhotos();
                if (!userPhotos.isEmpty()) {
                    UserPhotoEntity lastRectanglePhoto = Collections.max(userPhotos, Comparator.comparing(UserPhotoEntity::getDateCreated));
                    ratingDTO.setIdPhotoCircle(lastRectanglePhoto.getId());
                    ratingDTO.setPhotoRectangle(lastRectanglePhoto.getPhotoRectangle());
                    ratingDTO.setIdPhoto(lastRectanglePhoto.getId());
                }

                randomRatingDTOs.add(ratingDTO);
            }

            return randomRatingDTOs;
        });
    }

    public List<ProfileUserEntity> provideRandomUsers(long count, long idProfile) {
        List<ProfileUserEntity> randomUsers = new ArrayList<>();
        long maxId = profileUserRepository.getMaxId();
        while (randomUsers.size() < count) {
            long randomId = (long) (Math.random() * maxId) + 1L;
            Optional<ProfileUserEntity> randomUserOptional = profileUserRepository.findById(randomId);

            if (randomUserOptional.isPresent()) {
                ProfileUserEntity randomUser = randomUserOptional.get();
                if (!randomUser.getUserPhotos().isEmpty() && randomUser.getId() != idProfile ) {
                    randomUsers.add(randomUser);
                }
            }
        }
        return randomUsers;
    }

}