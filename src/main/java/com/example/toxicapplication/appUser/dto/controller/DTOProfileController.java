package com.example.toxicapplication.appUser.dto.controller;

import com.example.toxicapplication.appUser.dto.ProfileUserDTO;
import com.example.toxicapplication.appUser.dto.RatingDTO;
import com.example.toxicapplication.appUser.dto.TopDTO;
import com.example.toxicapplication.appUser.dto.service.DTOProfileService;
import com.example.toxicapplication.appUser.dto.service.DTORatingService;
import com.example.toxicapplication.appUser.dto.service.DTOTopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dto")
@AllArgsConstructor
public class DTOProfileController {
    private final DTOProfileService dTOProfileService;
    private final DTORatingService dtoRatingService;
    private final DTOTopService dtoTopService;

    @GetMapping("/{profileUserId}")
    public ResponseEntity<ProfileUserDTO> getDtoUser(@PathVariable Long profileUserId) {
        ProfileUserDTO profileUserDTO = dTOProfileService.getProfileUserDTO(profileUserId);
        return ResponseEntity.ok(profileUserDTO);
    }

    @GetMapping("/random/{count}")
    public ResponseEntity<List<RatingDTO>> getRandomRatings(@PathVariable Long count) {
        List<RatingDTO> randomRatings = dtoRatingService.getRandomRatingDTOs(count);
        return ResponseEntity.ok(randomRatings);
    }

    @GetMapping("/top")
    public ResponseEntity<List<TopDTO>> getAllTopDTOs() {
        List<TopDTO> topDTOs = dtoTopService.getAllTopDTOs();
        return ResponseEntity.ok(topDTOs);
    }
}
