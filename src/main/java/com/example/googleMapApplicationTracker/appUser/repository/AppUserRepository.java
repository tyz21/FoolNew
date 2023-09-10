package com.example.googleMapApplicationTracker.appUser.repository;

import com.example.googleMapApplicationTracker.appUser.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository
        extends JpaRepository<AppUser, Long> {
   Optional<AppUser> findByUserName(String userName);
   boolean existsByUserName(String userName);

}
