package com.example.toxicapplication.appUser.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
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
