package com.education.projects.places.manager.repository;

import com.education.projects.places.manager.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {
   /* @Query("select")
    Collection <User> findAllByBrand();*/
}
