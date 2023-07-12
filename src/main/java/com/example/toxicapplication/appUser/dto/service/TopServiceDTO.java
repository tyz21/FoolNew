package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.TopDTO;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TopServiceDTO {
    private final ProfileUserRepository profileUserRepository;
    private final TransactionTemplate transactionTemplate;
//    public TopDTO getTopDTO(Long profileUserId) {
//        return transactionTemplate.execute(status -> {
//            ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileUserId)
//                    .orElseThrow(() -> new EntityNotFoundException("Profile user not found"));
//
//            return convertToTopDTO(profileUserEntity);
//        });
//    }

    public List<TopDTO> getAllTopDTOs() {
        return transactionTemplate.execute(status -> {
            List<TopDTO> topDTOs = new ArrayList<>();
            int page = 0;
            int pageSize = 50;
            while (true) {
                Pageable pageable = PageRequest.of(page, pageSize);
                Page<ProfileUserEntity> resultPage = profileUserRepository.findAll(pageable);
                List<ProfileUserEntity> profileUsers = resultPage.getContent();

                for (ProfileUserEntity profileUserEntity : profileUsers) {
                    if (profileUserEntity.getUserPhotos().size() != 0) {
                        TopDTO topDTO = convertToTopDTO(profileUserEntity);
                        topDTOs.add(topDTO);
                    }
                }

                if (!resultPage.hasNext()) {
                    break;
                }
                page++;
            }
            topDTOs.sort(Comparator.comparingLong(TopDTO::getTopUser));

            return topDTOs;
        });
    }

    private TopDTO convertToTopDTO(ProfileUserEntity profileUserEntity) {
        return transactionTemplate.execute(status -> {
            TopDTO topDTO = new TopDTO();
            topDTO.setIdProfile(profileUserEntity.getId());
            topDTO.setProfileName(profileUserEntity.getProfileName());
            topDTO.setRatingUser(profileUserEntity.getRatingUser());
            topDTO.setTopUser(profileUserEntity.getTopUser());
            List<UserPhotoEntity> userPhotos = profileUserEntity.getUserPhotos();
            if (!userPhotos.isEmpty()) {
                UserPhotoEntity lastCirclePhoto = Collections.max(userPhotos, Comparator.comparing(UserPhotoEntity::getDateCreated));
                topDTO.setIdPhotoCircle(lastCirclePhoto.getId());
                topDTO.setLastCirclePhoto(lastCirclePhoto.getPhotoCircle());
            }

            return topDTO;
        });
    }
}
