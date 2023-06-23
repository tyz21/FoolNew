package com.example.toxicapplication.appUser.userPhoto.reposirory;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntityDemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserPhotoRepositoryDemo extends JpaRepository<UserPhotoEntityDemo, Long> {
}