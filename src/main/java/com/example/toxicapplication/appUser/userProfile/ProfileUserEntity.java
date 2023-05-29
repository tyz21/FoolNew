package com.example.toxicapplication.appUser.userProfile;

import com.example.toxicapplication.appUser.userDetails.AppUser;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Data
public class ProfileUserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToOne(fetch = FetchType.LAZY)
    private UserPhotoEntity userPhotoEntity;
    @Column
    private long lastIdAddPhoto;
    @Column
    private double ratingUser;
    @Column
    private long topUser;
    @Column
    private String subscriberId;
    @Column
    private String subscriptionId;
    @Column(name = "all_id_photo_user")
    private String allIdPhotoUser;

    public ProfileUserEntity(AppUser appUser) {
        this.appUser = appUser;
        this.allIdPhotoUser = "";
    }

    public void setRatingUser(double ratingUser) {
        this.ratingUser = ratingUser;
    }

    public List<Long> getAllIdPhotoUser() {
        if (allIdPhotoUser.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(allIdPhotoUser.split(""))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public void setAllIdPhotoUser(List<Long> photoIds) {
        this.allIdPhotoUser = photoIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }
}