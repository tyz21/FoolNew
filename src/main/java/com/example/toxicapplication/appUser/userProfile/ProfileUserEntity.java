package com.example.toxicapplication.appUser.userProfile;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_photo_id") // замените на соответствующее имя колонки
    private UserPhotoEntity userPhotoEntity;
    @Column
    private double ratingUser;
    @Column
    @OrderBy("topUser ASC")
    private long topUser;
    @Column
    private String subscriberId;
    @Column
    private String subscriptionId;
    @Column(name = "all_id_photo_circle")
    private String allIdCirclePhotoUser;
    @Column(name = "all_id_photo_rectangle")
    private String allIdRectanglePhotoUser;

    public ProfileUserEntity(AppUser appUser) {
        this.appUser = appUser;
        this.allIdCirclePhotoUser = "";
        this.allIdRectanglePhotoUser = "";
    }
    public void setRatingUser(double ratingUser) {
        this.ratingUser = ratingUser;
    }

    public List<Long> getAllIdCirclePhotoUser() {
        if (allIdCirclePhotoUser.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(allIdCirclePhotoUser.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public void setAllIdCirclePhotoUser(List<Long> photoIds) {
        this.allIdCirclePhotoUser = photoIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Long> getAllIdRectanglePhotoUser() {
        if (allIdRectanglePhotoUser.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(allIdRectanglePhotoUser.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public void setAllIdRectanglePhotoUser(List<Long> photoIds) {
        this.allIdRectanglePhotoUser = photoIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}