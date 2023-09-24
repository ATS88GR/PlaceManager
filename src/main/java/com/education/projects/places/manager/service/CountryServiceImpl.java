package com.education.projects.places.manager.service;

import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.entity.Country;
import com.education.projects.places.manager.entity.CountryPage;
import com.education.projects.places.manager.entity.CountrySearchCriteria;
import com.education.projects.places.manager.exception.CountryNotFoundException;
import com.education.projects.places.manager.mapper.CountryMapper;
import com.education.projects.places.manager.repository.CountryCriteriaRepository;
import com.education.projects.places.manager.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    CountryCriteriaRepository countryCriteriaRepository;

    /**
     * Gets all countries objects information from database
     *
     * @return The list of the Country objects
     */
    public Collection<CountryDtoResp> getAllCountries() {
        return countryMapper.countryListToCountryDtoList(countryRepository.findAll());
    }

    /**
     * Gets the Country DTO object information from the database by id
     *
     * @param id id of the country object in database
     * @return The Country DTO object from database
     * @throws Exception
     */
    public CountryDtoResp getCountryDtoById(UUID id) throws Exception {
        if (countryRepository.existsById(id))
            return countryMapper.countryToCountryDto(countryRepository.getReferenceById(id));
        throw new CountryNotFoundException(id);
    }

    /**
     * Gets the Country object information from the database by id
     *
     * @param id id of the country object in database
     * @return The Country object from database
     * @throws Exception
     */
    public Country getCountryById(UUID id) throws Exception {
        if (countryRepository.existsById(id))
            return countryRepository.getReferenceById(id);
        throw new CountryNotFoundException(id);
    }

    /**
     * @param countryPage           is an object with pagination settings
     * @param countrySearchCriteria is an object with search settings
     * @return List of users with pagination and search settings
     */
    public Page<CountryDtoResp> getSortFilterPaginCountries(CountryPage countryPage,
                                                            CountrySearchCriteria countrySearchCriteria) {
        return countryCriteriaRepository.findAllWithFilters(countryPage, countrySearchCriteria);
    }
}
