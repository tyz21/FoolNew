package com.example.toxicapplication.appUser.userPhoto.entity;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userProfile.ProfileUserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPhotoEntityDemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
    //  @JsonBackReference
    //@JsonIgnoreProperties("profileUserEntity")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_user_id")
    private ProfileUserEntity profileUserEntity;
    @Lob
   // @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "photo_rectangle")
    private byte[] photoRectangle;
    @Lob
    //@Type(type = "org.hibernate.type.ImageType")
    @Column(name = "photo_circle")
    private byte[] photoCircle;
    @JsonIgnore
    @Column
    private Integer countMark;
    @JsonIgnore
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
            List<UserPhotoEntityDemo> userPhotos = profileUser.getUserPhotos();
            if (!userPhotos.contains(this)) {
                userPhotos.add(this);
                this.setProfileUserEntity(profileUser);
            }
        }
    }
//    public UserPhotoEntityDemo(AppUser appUser) {
//        this.appUser = appUser;
//    }
    public UserPhotoEntityDemo(AppUser appUser, ProfileUserEntity profileUserEntity) {
        this.appUser = appUser;
        this.profileUserEntity = profileUserEntity;
    }
}
