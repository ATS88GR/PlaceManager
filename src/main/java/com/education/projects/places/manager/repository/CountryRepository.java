package com.education.projects.places.manager.repository;

import com.education.projects.places.manager.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository <Country, Integer> {
}
