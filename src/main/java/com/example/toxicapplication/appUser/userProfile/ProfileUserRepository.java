package com.example.toxicapplication.appUser.userProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileUserRepository extends JpaRepository<ProfileUserEntity, Long> {
    //  Optional<ProfileUserEntity> findByAppUser(AppUser appUser);
    ProfileUserEntity findAllById(Long id);

    ProfileUserEntity findProfileUserEntityById(Long id);

    @Query("SELECT MAX(u.id) FROM ProfileUserEntity u")
    Long getMaxId();

    // Метод для получения всех пользователей
  //  List<ProfileUserEntity> findAll();

    // Метод для обновления рейтинга и места в рейтинге пользователя
    @Modifying
    @Query("UPDATE ProfileUserEntity u SET u.ratingUser = :rating, u.topUser = :topInRating WHERE u.id = :id")
    void updateRatingAndTop(@Param("id") Long id, @Param("rating") double rating, @Param("topInRating") long topInRating);

}

