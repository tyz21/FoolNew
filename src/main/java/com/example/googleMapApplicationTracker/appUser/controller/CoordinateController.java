package com.example.googleMapApplicationTracker.appUser.controller;

import com.example.googleMapApplicationTracker.appUser.entity.Coordinate;
import com.example.googleMapApplicationTracker.appUser.service.CoordinateService;
import com.example.googleMapApplicationTracker.appUser.utility.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordinate")
@AllArgsConstructor
public class CoordinateController {
    private final CoordinateService coordinateService;
    @PostMapping("/save")
    public ApiResponse<String> saveCoordinate(@RequestBody CoordinateRequest coordinateRequest) {
        return coordinateService.saveCoordinate(coordinateRequest);
    }

    @GetMapping("/getAll")
    public ApiResponse<List<Coordinate>> getAllCoordinates() {
        return coordinateService.getAllCoordinates();
    }
}