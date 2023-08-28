package com.education.projects.places.manager.repository;

import com.education.projects.places.manager.dto.response.PlaceDtoResp;
import com.education.projects.places.manager.entity.Place;
import com.education.projects.places.manager.entity.PlacePage;
import com.education.projects.places.manager.entity.PlaceSearchCriteria;
import com.education.projects.places.manager.mapper.PlaceMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class PlaceCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    PlaceMapper placeMapper;

    public PlaceCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<PlaceDtoResp> findAllWithFilters(PlacePage placePage,
                                                 PlaceSearchCriteria placeSearchCriteria){
        CriteriaQuery<Place> criteriaQuery = criteriaBuilder.createQuery(Place.class);
        Root<Place> placeRoot = criteriaQuery.from(Place.class);
        Predicate predicate = getPredicate(placeSearchCriteria, placeRoot);
        criteriaQuery.where(predicate);

        setOrder(placePage, criteriaQuery, placeRoot);

        TypedQuery<Place> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(placePage.getPageNumber() * placePage.getPageSize());
        typedQuery.setMaxResults(placePage.getPageSize());

        Pageable pageable = getPageable(placePage);

        long placesCount = 10;

        return (new PageImpl<>(
                placeMapper.placeListToPlaceDtoList(typedQuery.getResultList()),
                pageable,
                placesCount));
    }

    private long getPlacesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Place> countRoot = countQuery.from(Place.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(PlacePage placePage) {
        Sort sort = Sort.by(placePage.getSortDirection(), placePage.getSortBy());
        return PageRequest.of(placePage.getPageNumber(), placePage.getPageSize(), sort);
    }

    private void setOrder(PlacePage placePage,
                          CriteriaQuery<Place> criteriaQuery,
                          Root<Place> placeRoot) {
        if(placePage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(placeRoot.get(placePage.getSortBy())));
        } else criteriaQuery.orderBy(criteriaBuilder.desc(placeRoot.get(placePage.getSortBy())));
    }

    private Predicate getPredicate(PlaceSearchCriteria placeSearchCriteria,
                                   Root<Place> placeRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(placeSearchCriteria.getAddress()))
            predicates.add(criteriaBuilder.like(placeRoot.get("address"),
                    "%" + placeSearchCriteria.getAddress() + "%"));
        if(Objects.nonNull(placeSearchCriteria.getCountryId()))
            predicates.add(criteriaBuilder.equal(placeRoot.get("country_id"),
                    placeSearchCriteria.getCountryId()));
        return criteriaBuilder.and(predicates.toArray((new Predicate[0])));
    }
}
