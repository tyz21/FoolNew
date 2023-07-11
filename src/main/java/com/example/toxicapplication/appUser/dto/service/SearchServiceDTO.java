package com.example.toxicapplication.appUser.dto.service;

import com.example.toxicapplication.appUser.dto.SearchDTO;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.example.toxicapplication.appUser.userProfile.ProfileUserRepository;
import com.example.toxicapplication.appUser.userProfile.comporator.ProfileComparator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class SearchServiceDTO {
    private final ProfileUserRepository profileUserRepository;
    private final TransactionTemplate transactionTemplate;

    public List<SearchDTO> searchUser(String requestSearch) {
        return transactionTemplate.execute(status -> {
            Pageable pageable = PageRequest.of(0, 50, Sort.by(Sort.Direction.ASC, "profileName"));
            Page<ProfileUserEntity> page = profileUserRepository.findAll(pageable);
            List<ProfileUserEntity> users = new ArrayList<>(page.getContent());
            users.sort(new ProfileComparator(requestSearch));

            List<SearchDTO> matchingUsers = new ArrayList<>();
            for (ProfileUserEntity user : users) {
                if (user == null || user.getProfileName() == null) {
                    continue;
                }
                if (user.getProfileName().toLowerCase().contains(requestSearch) || user.getProfileName().contains(requestSearch)) {
                    matchingUsers.add(convertToSearchDTO(user));
                }
            }

            return matchingUsers;
        });
    }

    private SearchDTO convertToSearchDTO(ProfileUserEntity profileUserEntity) {
        return transactionTemplate.execute(status -> {
            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setIdProfile(profileUserEntity.getId());
            searchDTO.setProfileName(profileUserEntity.getProfileName());
            List<UserPhotoEntityDemo> userPhotos = profileUserEntity.getUserPhotos();
            if (!userPhotos.isEmpty()) {
                UserPhotoEntityDemo lastCirclePhoto = Collections.max(userPhotos, Comparator.comparing(UserPhotoEntityDemo::getDateCreated));
                searchDTO.setIdPhotoCircle(lastCirclePhoto.getId());
                searchDTO.setLastCirclePhoto(lastCirclePhoto.getPhotoCircle());
            }
          //  searchDTO.setLastCirclePhoto(profileUserEntity.getUserPhotos().get(profileUserEntity.getUserPhotos().size() - 1).getPhotoCircle());

            return searchDTO;
        });
    }
}

