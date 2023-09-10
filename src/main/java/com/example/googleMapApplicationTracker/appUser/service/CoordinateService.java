package com.example.googleMapApplicationTracker.appUser.service;

import com.example.googleMapApplicationTracker.appUser.controller.CoordinateRequest;
import com.example.googleMapApplicationTracker.appUser.entity.Coordinate;
import com.example.googleMapApplicationTracker.appUser.repository.CoordinateRepository;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CoordinateService {
    private final CoordinateRepository coordinateRepository;

    @Transactional
    public ApiResponse<String> saveCoordinate(CoordinateRequest coordinateRequest) {
        if (coordinateRequest.getLat().equals("") || coordinateRequest.getLon().equals("") || coordinateRequest.getPing() == 0) {
            return new ApiResponse<>("Exception: coordinate didn't saved", true);
        }

        Coordinate coordinate = new Coordinate(coordinateRequest.getLat(), coordinateRequest.getLon(), coordinateRequest.getPing());
        coordinateRepository.save(coordinate);

        return new ApiResponse<>("Success!", false);
    }

    public ApiResponse<List<Coordinate>> getAllCoordinates() {
        List<Coordinate> coordinates = coordinateRepository.findAll();
//        if (coordinates.isEmpty()) {
//            return new ApiResponse<>("Exception: no coordinates", true);
//        }

        return new ApiResponse<>(coordinates, false);
    }
}

