package com.example.toxicapplication.appUser.userPhoto.reposirory;

import com.example.toxicapplication.appUser.userPhoto.entity.UserPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserPhotoRepository extends JpaRepository<UserPhotoEntity, Long> {
//    @Query("SELECT p.id FROM UserPhotoEntity p WHERE p.id = :userId")
//    List<Long> findImageIdsByUserId(@Param("userId") Long userId);

    UserPhotoEntity findUserPhotoEntityById(long id);
}
