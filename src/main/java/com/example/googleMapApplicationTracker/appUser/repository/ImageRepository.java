package com.example.googleMapApplicationTracker.appUser.repository;

import com.example.googleMapApplicationTracker.appUser.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
