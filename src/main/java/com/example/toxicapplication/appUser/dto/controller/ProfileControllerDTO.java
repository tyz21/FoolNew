package com.example.toxicapplication.appUser.dto.controller;

import com.example.toxicapplication.appUser.dto.*;
import com.example.toxicapplication.appUser.dto.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dto")
@AllArgsConstructor
public class ProfileControllerDTO {
    private final ProfileServiceDTO dTOProfileServiceDTO;
    private final RatingServiceDTO ratingServiceDTO;
    private final TopServiceDTO topServiceDTO;
    private final SearchServiceDTO searchServiceDTO;
    private final SubscribeServiceDTO subscribeServiceDTO;

    @GetMapping("/{profileUserId}")
    public ResponseEntity<ProfileUserDTO> getDtoUser(@PathVariable Long profileUserId) {
        ProfileUserDTO profileUserDTO = dTOProfileServiceDTO.getProfileUserDTO(profileUserId);
        return ResponseEntity.ok(profileUserDTO);
    }

    @GetMapping("/random/{count}")
    public ResponseEntity<List<RatingDTO>> getRandomRatings(@PathVariable Long count) {
        if (ratingServiceDTO.getRandomRatingDTOs(count) != null) {
            List<RatingDTO> randomRatings = ratingServiceDTO.getRandomRatingDTOs(count);
            return ResponseEntity.ok(randomRatings);
        } else {
            return ResponseEntity.ok(new ArrayList<>()); // ToDo check empty list
        }
    }

    @GetMapping("/top")
    public ResponseEntity<List<TopDTO>> getAllTopDTOs() {
        List<TopDTO> topDTOs = topServiceDTO.getAllTopDTOs();
        return ResponseEntity.ok(topDTOs);
    }

    @GetMapping("/search/{requestSearch}")
    public List<SearchDTO> requestSearch(@PathVariable String requestSearch) {
        return searchServiceDTO.searchUser(requestSearch);
    }

    @GetMapping("/subscribe/{idUser}")
    public List<SubscribeDTO> getSubscriber(@PathVariable Long idUser) {
        return subscribeServiceDTO.getSubscriber(idUser);
    }
}
