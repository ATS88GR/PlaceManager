package com.education.projects.places.manager.mapper;

import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDtoResp countryToCountryDto(Country country);
    List<CountryDtoResp> countryListToCountryDtoList(List<Country> countries);
}
