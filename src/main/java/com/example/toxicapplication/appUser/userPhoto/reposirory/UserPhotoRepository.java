package com.example.toxicapplication.appUser.userPhoto.reposirory;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPhotoRepository extends JpaRepository<UserPhotoEntity, Long> {


}
