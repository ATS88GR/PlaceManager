package com.education.projects.places.manager.service;

import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.entity.Country;

import java.util.Collection;

public interface CountryService {

    Collection<CountryDtoResp> getAllCountries() throws Exception;
    CountryDtoResp getCountryDtoById(Integer id) throws Exception;
    Country getCountryById(Integer id) throws Exception;
}
