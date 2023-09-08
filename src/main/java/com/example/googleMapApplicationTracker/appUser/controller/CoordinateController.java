package com.example.googleMapApplicationTracker.appUser.controller;

import com.example.googleMapApplicationTracker.appUser.entity.Coordinate;
import com.example.googleMapApplicationTracker.appUser.service.CoordinateService;
import com.example.googleMapApplicationTracker.exception.NoCoordinateException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordinate")
@AllArgsConstructor
public class CoordinateController {
    private final CoordinateService coordinateService;

    @PostMapping("/save") //  /save?lat=42.123&lon=-71.456&ping=50
    public void saveCoordinate(@RequestParam String lat,
                               @RequestParam String lon,
                               @RequestParam String ping) throws NoCoordinateException {
        if (lat.equals("")) {
            throw new NoCoordinateException("latitude is empty");
        }
        if (lon.equals("")) {
            throw new NoCoordinateException("longitude is empty");
        }
        if (ping.equals("")) {
            throw new NoCoordinateException("ping is empty");
        }

        coordinateService.saveCoordinate(lat, lon, ping);
    }

    @GetMapping("/getAll")
    public List<Coordinate> getAllCoordinates() {
        return coordinateService.getAllCoordinates();
    }
}
