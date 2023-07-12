package com.example.toxicapplication.appUser.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProfileUserDTO {
    private Long idProfile;
    private String profileName;
    private double ratingUser;
    private long topUser;
    private Long idPhotoCircle;
    private byte [] lastCirclePhoto;
    private List<UserPhotoDTO> userPhotos;

}
