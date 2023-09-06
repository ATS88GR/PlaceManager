package com.education.projects.places.manager.mapper;

import com.education.projects.places.manager.response.dto.CountryDtoResp;
import com.education.projects.places.manager.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDtoResp countryToCountryDto(Country country);
    List<CountryDtoResp> countryListToCountryDtoList(List<Country> countries);
}
