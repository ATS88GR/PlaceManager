package com.education.projects.places.manager.repository;

import com.education.projects.places.manager.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer>, JpaSpecificationExecutor<Place> {
   /* @Query("select")
    Collection <User> findAllByBrand();*/
}
