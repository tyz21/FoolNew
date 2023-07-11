package com.example.toxicapplication.appUser.dto;

import lombok.Data;

@Data
public class UserPhotoDTO{
    private long idPhoto;
    private byte[] photoRectangle;
    private String dateCreated;
    private Integer countMark;
    private Double sumMark;
    private double ratingPhoto;
    private long topPhoto;

}
