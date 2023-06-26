package com.example.toxicapplication.appUser.dto;

import lombok.Data;

@Data
public class RatingDTO {
    private Long idPhoto;
    private String profileName;
    private double ratingUser;
    private long topUser;
    private byte[] photoRectangle;
}
