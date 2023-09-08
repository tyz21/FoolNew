package com.example.googleMapApplicationTracker.appUser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String latitude;
    private String longitude;
    private String ping;

    public Coordinate(String latitude, String longitude, String ping) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ping = ping;
    }
}
