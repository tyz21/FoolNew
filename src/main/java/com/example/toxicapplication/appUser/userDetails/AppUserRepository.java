package com.example.toxicapplication.appUser.userDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository
        extends JpaRepository<AppUser, Long> {
   // Optional<AppUser> findByEmail(String email);
 Optional<AppUser> findByUserName(String userName);
   // boolean existsByEmail(String email);
   boolean existsByUserName(String userName);
    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    boolean existsByEmail(String email);
}
