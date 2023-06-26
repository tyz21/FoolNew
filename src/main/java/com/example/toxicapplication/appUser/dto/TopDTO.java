package com.example.toxicapplication.appUser.dto;

import lombok.Data;
@Data
public class TopDTO {
    private Long idProfile;
    private String profileName;
    private double ratingUser;
    private long topUser;
    private byte [] lastCirclePhoto;
}
