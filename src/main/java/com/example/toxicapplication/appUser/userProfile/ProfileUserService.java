package com.example.toxicapplication.appUser.userProfile;

import com.example.toxicapplication.appUser.userProfile.comporator.ProfileComparator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProfileUserService {
    private final ProfileUserRepository profileUserRepository;

    public List<ProfileUserEntity> searchUser(String requestSearch) {
        Pageable pageable = PageRequest.of(0, 50, Sort.by(Sort.Direction.ASC, "profileName"));
        Page<ProfileUserEntity> page = profileUserRepository.findAll(pageable);
        List<ProfileUserEntity> users = new ArrayList<>(page.getContent());
        users.sort(new ProfileComparator(requestSearch));

        List<ProfileUserEntity> matchingUsers = new ArrayList<>();
        for (ProfileUserEntity user : users) {
            if (user == null || user.getProfileName() == null) {
                continue;
            }
            if (user.getProfileName().toLowerCase().contains(requestSearch) || user.getProfileName().contains(requestSearch)) {
                matchingUsers.add(user);
            }
        }

        return matchingUsers;
    }

    public List<ProfileUserEntity> getAllTopUsers() {
        List<ProfileUserEntity> users = new ArrayList<>();
        int page = 0;
        int pageSize = 50;
        Sort sort = Sort.by(Sort.Direction.ASC, "topUser");

        while (true) {
            Pageable pageable = PageRequest.of(page, pageSize, sort);
            Page<ProfileUserEntity> resultPage = profileUserRepository.findAll(pageable);
            List<ProfileUserEntity> profileUsers = resultPage.getContent();

            users.addAll(profileUsers);

            if (!resultPage.hasNext()) {
                break;
            }
            page++;
        }

        return users;
    }

    public ProfileUserEntity getProfile(Long profileId) {
        return profileUserRepository.findById(profileId).orElse(null);
    }
}
