package com.example.toxicapplication.appUser.dto.controller;

import com.example.toxicapplication.appUser.dto.ProfileUserDTO;
import com.example.toxicapplication.appUser.dto.service.DTOUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dto")
@AllArgsConstructor
public class DTOUserController {
    private final DTOUserService dTOUserService;

    @GetMapping("/{profileUserId}")
    public ResponseEntity<ProfileUserDTO> getDtoUser(@PathVariable Long profileUserId) {
        ProfileUserDTO profileUserDTO = dTOUserService.getProfileUserDTO(profileUserId);
        return ResponseEntity.ok(profileUserDTO);
    }
}
