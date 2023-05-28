package com.example.toxicapplication.appUser.userProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileUserRepository extends JpaRepository<ProfileUserEntity, Long> {
  //  Optional<ProfileUserEntity> findByAppUser(AppUser appUser);
    ProfileUserEntity findAllById(Long id);
    ProfileUserEntity findProfileUserEntityById(Long id);
}
