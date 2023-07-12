package com.example.toxicapplication.appUser.userPhoto.reposirory;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserPhotoRepository extends JpaRepository<UserPhotoEntity, Long> {
}