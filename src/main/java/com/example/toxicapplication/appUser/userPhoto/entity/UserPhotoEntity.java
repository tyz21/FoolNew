package com.example.toxicapplication.appUser.userPhoto.entity;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_user_id")
    private ProfileUserEntity profileUserEntity;

    @Column
    private String pathPhotoRectangle;

    @Column
    private String pathPhotoCircle;

    @Column
    private Integer countMark;

    @Column
    private Double sumMark;
    @Column
    private double ratingPhoto;

    @Column
    private long topPhoto;

    @PrePersist
    private void updateProfileUserEntity() {
        if (appUser != null && id != null) {
            ProfileUserEntity profileUser = appUser.getProfileUserEntity();
            if (profileUser == null) {
                profileUser = new ProfileUserEntity(appUser);
                appUser.setProfileUserEntity(profileUser);
            }
            profileUser.setUserPhotoEntity(this);
        }
    }

    //    public UserPhotoEntity(AppUser appUser, String imagePath) {
//        this.appUser = appUser;
//        if (imagePath.contains("/rectangle")) {
//            this.pathPhotoRectangle = imagePath;
//        }
//        if (imagePath.contains("/circle")) {
//            this.pathPhotoCircle = imagePath;
//        }
//    }
    public UserPhotoEntity(AppUser appUser) {
        this.appUser = appUser;
    }
}