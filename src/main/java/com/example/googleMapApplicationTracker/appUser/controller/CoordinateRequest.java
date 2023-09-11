package com.example.googleMapApplicationTracker.appUser.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoordinateRequest {
    private int id;
    private String latitude;
    private String longitude;
    private int ping;

}
