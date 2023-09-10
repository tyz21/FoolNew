package com.example.googleMapApplicationTracker.appUser.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoordinateRequest {
    private int id;
    private String lat;
    private String lon;
    private int ping;

}
