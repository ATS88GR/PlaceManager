package com.education.projects.places.manager.service;

import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.entity.Country;
import com.education.projects.places.manager.dto.request.CountryPage;
import com.education.projects.places.manager.dto.request.CountrySearchCriteria;
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
