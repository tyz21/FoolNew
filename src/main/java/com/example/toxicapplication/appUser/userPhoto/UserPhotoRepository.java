package com.example.toxicapplication.appUser.userPhoto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPhotoRepository extends JpaRepository<UserPhotoEntity, Long> {
    @Query("SELECT p.id FROM UserPhotoEntity p WHERE p.id = :userId")
    List<Long> findImageIdsByUserId(@Param("userId") Long userId);
}
