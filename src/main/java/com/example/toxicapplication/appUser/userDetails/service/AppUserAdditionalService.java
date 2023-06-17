package com.example.toxicapplication.appUser.userDetails.service;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userDetails.entity.comporator.UsernameComparator;
import com.example.toxicapplication.appUser.userDetails.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppUserAdditionalService {
    private final AppUserRepository appUserRepository;

    @Transactional(readOnly = true)
    public long getIdUser(String userName) {
        AppUser appUser = appUserRepository.findByUserName(userName).get();
        return appUser.getId();
    }
    public String searchUser(String requestSearch) {
        StringBuilder users = new StringBuilder();
        Pageable pageable = PageRequest.of(0, 50, Sort.by(Sort.Direction.ASC, "userName"));
        Page<AppUser> page = appUserRepository.findAll(pageable);
        List<AppUser> appUsers = new ArrayList<>(page.getContent());

        appUsers.sort(new UsernameComparator(requestSearch));

        for (AppUser user : appUsers) {
            if (user.getUsername().toLowerCase().contains(requestSearch) || user.getUsername().contains(requestSearch)) {
                users.append(user.getId()).append(", ");
            }
        }

        return users.toString();
    }

}
