package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.RatingDTO;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DTORatingService {
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

                List<UserPhotoEntityDemo> userPhotos = profileUserEntity.getUserPhotos();
                if (!userPhotos.isEmpty()) {
                    UserPhotoEntityDemo lastUserPhoto = userPhotos.get(userPhotos.size() - 1);
                    ratingDTO.setIdPhoto(lastUserPhoto.getId());
                    ratingDTO.setPhotoRectangle(lastUserPhoto.getPhotoRectangle());
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
//    public RatingDTO getRatingDTO(Long profileUserId) {
//        return transactionTemplate.execute(status -> {
//            ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileUserId)
//                    .orElseThrow(() -> new EntityNotFoundException("Profile user not found"));
//
//            RatingDTO ratingDTO = new RatingDTO();
//            ratingDTO.setProfileName(profileUserEntity.getProfileName());
//            ratingDTO.setRatingUser(profileUserEntity.getRatingUser());
//            ratingDTO.setTopUser(profileUserEntity.getTopUser());
//
//            List<UserPhotoEntityDemo> userPhotos = profileUserEntity.getUserPhotos();
//            if (!userPhotos.isEmpty()) {
//                UserPhotoEntityDemo lastUserPhoto = userPhotos.get(userPhotos.size() - 1);
//                ratingDTO.setPhotoRectangle(lastUserPhoto.getPhotoRectangle());
//            }
//
//            return ratingDTO;
//        });
//    }
//
//    public List<ProfileUserEntity> provideRandomUsers() throws NoPhotoForProfileException {
//        List<ProfileUserEntity> randomUsers = new ArrayList<>();
//        ProfileUserEntity randomUser;
//        long maxId = profileUserRepository.getMaxId();
//        while (randomUsers.size() < 20) {
//            long randomId = (long) (Math.random() * maxId) + 1L;
//            randomUser = profileUserRepository.findById(randomId).get();
//
//            if (randomUser != null) {
//                randomUsers.add(randomUser);
//            }
//        }
//
//        return randomUsers;
//    }
}