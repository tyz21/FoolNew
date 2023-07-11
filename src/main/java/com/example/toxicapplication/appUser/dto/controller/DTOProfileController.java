package com.example.toxicapplication.appUser.dto.controller;

import com.example.toxicapplication.appUser.dto.*;
import com.example.toxicapplication.appUser.dto.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dto")
@AllArgsConstructor
public class DTOProfileController {
    private final DTOProfileService dTOProfileService;
    private final DTORatingService dtoRatingService;
    private final DTOTopService dtoTopService;
    private final DTOSearchService dtoSearchService;
    private final SubscribeServiceDTO subscribeServiceDTO;

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

    @GetMapping("/search/{requestSearch}")
    public List<SearchDTO> requestSearch(@PathVariable String requestSearch) {
        return dtoSearchService.searchUser(requestSearch);
    }

    @GetMapping("/subscribe/{idUser}")
    public List<SubscribeDTO> getSubscriber(@PathVariable Long idUser){
        return subscribeServiceDTO.getSubscriber(idUser);
    }
}
