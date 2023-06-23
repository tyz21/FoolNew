package com.example.toxicapplication.appUser.userProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProfileUserRepository extends JpaRepository<ProfileUserEntity, Long> {
    @Query("SELECT MAX(u.id) FROM ProfileUserEntity u")
    Long getMaxId();
}

