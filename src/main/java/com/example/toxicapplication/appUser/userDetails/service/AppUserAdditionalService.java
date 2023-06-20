package com.example.toxicapplication.appUser.userDetails.service;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userDetails.repository.AppUserRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AppUserAdditionalService {
    private final AppUserRepository appUserRepository;

    @Transactional(readOnly = true)
    public long getIdUser(String userName) {
        AppUser appUser = appUserRepository.findByUserName(userName).get();
        return appUser.getId();
    }

}
