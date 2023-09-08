package com.example.googleMapApplicationTracker.appUser.service;

import com.example.googleMapApplicationTracker.appUser.entity.Coordinate;
import com.example.googleMapApplicationTracker.appUser.repository.CoordinateRepository;
import com.example.googleMapApplicationTracker.exception.NoCoordinateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CoordinateService {
private final CoordinateRepository coordinateRepository;
    public void saveCoordinate(String lat, String lon, String ping) {
        Coordinate coordinate = new Coordinate(lat, lon, ping);
        coordinateRepository.save(coordinate);
    }

    public List<Coordinate> getAllCoordinates() {
        return coordinateRepository.findAll();
    }
}
