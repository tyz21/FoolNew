package com.example.toxicapplication.appUser.dto;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import lombok.Data;

import java.util.List;

@Data
public class ProfileUserDTO {
    private AppUser appUser;
    private String profileName;
    private double ratingUser;
    private long topUser;
    private List<UserPhotoDTO> userPhotos;
}
