package com.education.projects.places.manager.repository;

import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.entity.Country;
import com.education.projects.places.manager.entity.CountryPage;
import com.education.projects.places.manager.entity.CountrySearchCriteria;
import com.education.projects.places.manager.mapper.CountryMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CountryCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    CountryMapper countryMapper;

    public CountryCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<CountryDtoResp> findAllWithFilters(CountryPage countryPage,
                                                   CountrySearchCriteria countrySearchCriteria) {
        CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
        Root<Country> countryRoot = criteriaQuery.from(Country.class);
        Predicate predicate = getPredicate(countrySearchCriteria, countryRoot);
        criteriaQuery.where(predicate);

        setOrder(countryPage, criteriaQuery, countryRoot);

        TypedQuery<Country> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(countryPage.getPageNumber() * countryPage.getPageSize());
        typedQuery.setMaxResults(countryPage.getPageSize());

        Pageable pageable = getPageable(countryPage);

        long countriesCount = getCountriesCount();

        return (new PageImpl<>(
                countryMapper.countryListToCountryDtoList(typedQuery.getResultList()),
                pageable,
                countriesCount));
    }

    private long getCountriesCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Country> countRoot = countQuery.from(Country.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(CountryPage countryPage) {
        Sort sort = Sort.by(countryPage.getSortDirection(), countryPage.getSortBy());
        return PageRequest.of(countryPage.getPageNumber(), countryPage.getPageSize(), sort);
    }

    private void setOrder(CountryPage countryPage,
                          CriteriaQuery<Country> criteriaQuery,
                          Root<Country> countryRoot) {
        if (countryPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(countryRoot.get(countryPage.getSortBy())));
        } else criteriaQuery.orderBy(criteriaBuilder.desc(countryRoot.get(countryPage.getSortBy())));
    }

    private Predicate getPredicate(CountrySearchCriteria countrySearchCriteria,
                                   Root<Country> countryRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(countrySearchCriteria.getCountryDescr()))
            predicates.add(criteriaBuilder.like(countryRoot.get("countryDescr"),
                    "%" + countrySearchCriteria.getCountryDescr() + "%"));
        return criteriaBuilder.and(predicates.toArray((new Predicate[0])));
    }
}
