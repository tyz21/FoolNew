package com.example.toxicapplication.appUser.userProfile;

import com.example.toxicapplication.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileUserRepository extends JpaRepository<ProfileUserEntity, Long> {
  //  Optional<ProfileUserEntity> findByAppUser(AppUser appUser);

}
