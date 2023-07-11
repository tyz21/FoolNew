package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.RatingDTO;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
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

    public List<RatingDTO> getRandomRatingDTOs(long count) {
        return transactionTemplate.execute(status -> {
            List<RatingDTO> randomRatingDTOs = new ArrayList<>();
            List<ProfileUserEntity> randomUsers = provideRandomUsers(count);

            for (ProfileUserEntity profileUserEntity : randomUsers) {
                RatingDTO ratingDTO = new RatingDTO();
                ratingDTO.setProfileName(profileUserEntity.getProfileName());
                ratingDTO.setRatingUser(profileUserEntity.getRatingUser());
                ratingDTO.setTopUser(profileUserEntity.getTopUser());
                ratingDTO.setIdProfile(profileUserEntity.getId());
                List<UserPhotoEntityDemo> userPhotos = profileUserEntity.getUserPhotos();
                if (!userPhotos.isEmpty()) {
                    UserPhotoEntityDemo lastRectanglePhoto = Collections.max(userPhotos, Comparator.comparing(UserPhotoEntityDemo::getDateCreated));
                    ratingDTO.setIdPhotoCircle(lastRectanglePhoto.getId());
                    ratingDTO.setPhotoRectangle(lastRectanglePhoto.getPhotoRectangle());
                    ratingDTO.setIdPhoto(lastRectanglePhoto.getId());
                }

                randomRatingDTOs.add(ratingDTO);
            }

            return randomRatingDTOs;
        });
    }

    public List<ProfileUserEntity> provideRandomUsers(long count) {
        List<ProfileUserEntity> randomUsers = new ArrayList<>();
        long maxId = profileUserRepository.getMaxId();
        while (randomUsers.size() < count) {
            long randomId = (long) (Math.random() * maxId) + 1L;
            Optional<ProfileUserEntity> randomUserOptional = profileUserRepository.findById(randomId);

            if (randomUserOptional.isPresent()) {
                ProfileUserEntity randomUser = randomUserOptional.get();
                if (!randomUser.getUserPhotos().isEmpty()) {
                    randomUsers.add(randomUser);
                }
            }
        }

        return randomUsers;
    }

}