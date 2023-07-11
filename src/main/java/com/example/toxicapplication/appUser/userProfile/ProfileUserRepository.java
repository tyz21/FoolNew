package com.example.toxicapplication.appUser.userProfile;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProfileUserRepository extends JpaRepository<ProfileUserEntity, Long> {
    @Query("SELECT MAX(u.id) FROM ProfileUserEntity u")
    Long getMaxId();
    @Query("SELECT DISTINCT p FROM ProfileUserEntity p LEFT JOIN FETCH p.userPhotos WHERE p.userPhotos.size > 0 ORDER BY p.topUser ASC")
    List<ProfileUserEntity> findAllWithPhotosAndSorting(Sort sort);
}

