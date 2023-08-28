package com.education.projects.places.manager.service;

import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.entity.Country;
import com.education.projects.places.manager.mapper.CountryMapper;
import com.education.projects.places.manager.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryMapper countryMapper;

    /**
     * Gets all countrys objects information from database
     *
     * @return The list of the Country objects
     */
    public Collection<CountryDtoResp> getAllCountries() throws Exception{
        try {
            return countryMapper.countryListToCountryDtoList(countryRepository.findAll());
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Gets the Country object information from the database by id
     *
     * @param id id of the country object in database
     * @return The Country object from database
     * @throws Exception
     */
    public CountryDtoResp getCountryDtoById(Integer id) throws Exception {
        try {
            if (countryRepository.existsById(id))
                return countryMapper.countryToCountryDto(countryRepository.getReferenceById(id));
            else {
                Exception e = new Exception("The country wasn't found");
                log.error("Error: {}", e.getMessage());
                throw e;
            }
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public Country getCountryById(Integer id) throws Exception {
        try {
            if (countryRepository.existsById(id))
                return countryRepository.getReferenceById(id);
            else {
                Exception e = new Exception("The country wasn't found");
                log.error("Error: {}", e.getMessage());
                throw e;
            }
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
