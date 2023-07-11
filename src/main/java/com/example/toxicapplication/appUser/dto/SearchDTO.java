package com.example.toxicapplication.appUser.dto;

import lombok.Data;

@Data
public class SearchDTO {
    private Long idProfile;
    private String profileName;
    private Long idPhotoCircle;
    private byte [] lastCirclePhoto;
}
