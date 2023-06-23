package com.example.toxicapplication.appUser.dto;

import lombok.Data;

@Data
public class UserPhotoDTO {
    private byte[] photoRectangle;
    private byte[] photoCircle;
    private Integer countMark;
    private Double sumMark;
    private double ratingPhoto;
    private long topPhoto;
}
