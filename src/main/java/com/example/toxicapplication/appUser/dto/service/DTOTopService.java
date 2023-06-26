package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.TopDTO;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DTOTopService {
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
        Sort sort = Sort.by(Sort.Direction.ASC, "topUser");

        while (true) {
            Pageable pageable = PageRequest.of(page, pageSize, sort);
            Page<ProfileUserEntity> resultPage = profileUserRepository.findAll(pageable);
            List<ProfileUserEntity> profileUsers = resultPage.getContent();

            for (ProfileUserEntity profileUserEntity : profileUsers) {
                TopDTO topDTO = convertToTopDTO(profileUserEntity);
                topDTOs.add(topDTO);
            }

            if (!resultPage.hasNext()) {
                break;
            }
            page++;
        }

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
        topDTO.setLastCirclePhoto(profileUserEntity.getUserPhotos().get(profileUserEntity.getUserPhotos().size() - 1).getPhotoCircle());

        return topDTO;
        });
    }
}
//    public TopDTO getTopDTO(Long profileUserId) {
//        return transactionTemplate.execute(status -> {
//            ProfileUserEntity profileUserEntity = profileUserRepository.findById(profileUserId)
//                    .orElseThrow(() -> new EntityNotFoundException("Profile user not found"));
//
//            TopDTO topDTO = new TopDTO();
//            topDTO.setProfileName(profileUserEntity.getProfileName());
//            topDTO.setRatingUser(profileUserEntity.getRatingUser());
//            topDTO.setTopUser(profileUserEntity.getTopUser());
//            topDTO.setLastCirclePhoto(profileUserEntity.getUserPhotos().get(profileUserEntity.getUserPhotos().size() - 1).getPhotoCircle());
//
//            return topDTO;
//        });
//    }
//
//    public List<ProfileUserEntity> getAllTopUsers() {
//        List<ProfileUserEntity> users = new ArrayList<>();
//        int page = 0;
//        int pageSize = 50;
//        Sort sort = Sort.by(Sort.Direction.ASC, "topUser");
//
//        while (true) {
//            Pageable pageable = PageRequest.of(page, pageSize, sort);
//            Page<ProfileUserEntity> resultPage = profileUserRepository.findAll(pageable);
//            List<ProfileUserEntity> profileUsers = resultPage.getContent();
//
//            users.addAll(profileUsers);
//
//            if (!resultPage.hasNext()) {
//                break;
//            }
//            page++;
//        }
//
//        return users;
//    }
//}
