package com.example.googleMapApplicationTracker.appUser.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoordinateRequest {
    private int id;
    private double latitude;
    private double longitude;
    private int ping;

}
