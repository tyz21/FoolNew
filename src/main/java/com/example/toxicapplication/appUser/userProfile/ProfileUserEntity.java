package com.example.toxicapplication.appUser.userProfile;

import com.example.toxicapplication.appUser.userDetails.entity.AppUser;
import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Data
public class ProfileUserEntity implements Comparable<ProfileUserEntity>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "profileUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPhotoEntity> userPhotos = new ArrayList<>();
    @Column
    private String profileName;
    @Column
    private double ratingUser;
    @Column
    //@OrderBy("topUser ASC")
    private long topUser;
//    @OneToMany(mappedBy = "profileUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Subscriber> subscribers = new HashSet<>();
    @Column
    private String subscriptionId;
    @Column(name = "all_id_photo")
    private String allIdPhotoUser;
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
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

    @Override
    public int compareTo(ProfileUserEntity other) {
        return Long.compare(this.topUser, other.topUser);
    }
}