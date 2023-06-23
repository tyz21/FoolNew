package com.example.toxicapplication.appUser.userProfile;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
//import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Data
public class ProfileUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    //@JsonManagedReference
    @OneToMany(mappedBy = "profileUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPhotoEntityDemo> userPhotos = new ArrayList<>();
    @Column
    private String profileName;
    @Column
    private double ratingUser;
    @Column
    @OrderBy("topUser ASC")
    private long topUser;
    @Column
    private String subscriberId;
    @Column
    private String subscriptionId;
    @Column(name = "all_id_photo")
    private String allIdPhotoUser;

    public ProfileUserEntity(AppUser appUser) {
        this.appUser = appUser;
        this.allIdPhotoUser = "";
    }

    public List<Long> getAllIdPhotoUser() {
        if (allIdPhotoUser == null || allIdPhotoUser.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(allIdPhotoUser.split(","))
                .filter(s -> !s.isEmpty()) // Исключить пустые строки
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public void setAllIdPhotoUser(List<Long> photoIds) {
        this.allIdPhotoUser = photoIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

}