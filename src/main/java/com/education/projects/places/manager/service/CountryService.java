package com.education.projects.places.manager.service;

import com.education.projects.places.manager.entity.CountryPage;
import com.education.projects.places.manager.entity.CountrySearchCriteria;
import com.education.projects.places.manager.response.dto.CountryDtoResp;
import com.education.projects.places.manager.entity.Country;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.UUID;

public interface CountryService {

    Collection<CountryDtoResp> getAllCountries() throws Exception;
    CountryDtoResp getCountryDtoById(UUID id) throws Exception;
    Country getCountryById(UUID id) throws Exception;
    Page<CountryDtoResp> getSortFilterPaginCountries(CountryPage countryPage,
                                                CountrySearchCriteria countrySearchCriteria)
            throws Exception;
}
