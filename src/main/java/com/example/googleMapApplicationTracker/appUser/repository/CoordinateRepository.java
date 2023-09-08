package com.example.googleMapApplicationTracker.appUser.repository;

import com.example.googleMapApplicationTracker.appUser.entity.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CoordinateRepository
        extends JpaRepository<Coordinate, Long> {
}
