package com.example.toxicapplication.appUser.userPhoto.entity;

import com.example.toxicapplication.appUser.userDetails.AppUser;
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

    @Column
    private String pathPhotoRectangle;

    @Column
    private String pathPhotoCircle;

    @Column
    private double ratingPhoto;

    @Column
    private Long topPhoto;

    @PrePersist
    private void updateProfileUserEntity() {
        if (appUser != null && id != null) {
            ProfileUserEntity profileUser = appUser.getProfileUserEntity();
            if (profileUser == null) {
                profileUser = new ProfileUserEntity(appUser);
                appUser.setProfileUserEntity(profileUser);
            }
            profileUser.getAllIdPhotoUser().add(id);
        }
    }
    public UserPhotoEntity(AppUser appUser, String imagePath) {
        this.appUser = appUser;
        if (imagePath.contains("/rectangle")) {
            this.pathPhotoRectangle = imagePath;
        } else {
            this.pathPhotoCircle = imagePath;
        }
    }
}