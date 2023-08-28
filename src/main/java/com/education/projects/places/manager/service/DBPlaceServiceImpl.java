package com.education.projects.places.manager.service;

import com.education.projects.places.manager.dto.request.PlaceDtoReq;
import com.education.projects.places.manager.dto.response.PlaceDtoResp;
import com.education.projects.places.manager.entity.Place;
import com.education.projects.places.manager.entity.PlacePage;
import com.education.projects.places.manager.entity.PlaceSearchCriteria;
import com.education.projects.places.manager.mapper.PlaceMapper;
import com.education.projects.places.manager.repository.PlaceCriteriaRepository;
import com.education.projects.places.manager.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * The class for service Place information in database
 */
@Service
@Slf4j
public class DBPlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private CountryServiceImpl countryServiceImpl;
    @Autowired
    private PlaceCriteriaRepository placeCriteriaRepository;
    @Autowired
    private PlaceMapper placeMapper;

    /**
     * Creates a new Place object information in the database, returns a Place object from database by id
     *
     * @param placeDtoReq Place object to be added to the database
     * @return Place object information from database by id
     * @throws Exception
     */
    public PlaceDtoResp createPlace(PlaceDtoReq placeDtoReq) throws Exception {
        try {
            Place place = placeMapper.placeDtoToPlace(placeDtoReq);
            place.setCountry(countryServiceImpl.getCountryById(placeDtoReq.getCountryId()));

            PlaceDtoResp placeDtoResp = placeMapper.placeToPlaceDto(
                    placeRepository.save(place));
            placeDtoResp.setCountryId(place.getCountry().getId());
            return placeDtoResp;
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Updates the Place object information by id with update place information
     *
     * @param placeDtoReq Place object information to update
     * @param id  id of the place object to be updated
     * @return Place object information from database by id
     * @throws Exception
     */
    public PlaceDtoResp updatePlace(PlaceDtoReq placeDtoReq, Integer id) throws Exception {
        try {
            if (placeRepository.existsById(id)) {
                Place placeToChange = placeMapper.placeDtoToPlace(placeDtoReq);
                placeToChange.setCountry(countryServiceImpl.getCountryById(placeDtoReq.getCountryId()));
                placeToChange.setId(id);
                PlaceDtoResp placeDtoResp = placeMapper.placeToPlaceDto(
                        placeRepository.save(placeToChange));
                placeDtoResp.setCountryId(placeToChange.getCountry().getId());
                return placeDtoResp;
            } else {
                Exception e = new Exception("The place wasn't found");
                log.error("Error: {}", e.getMessage());
                throw e;
            }
        }catch (Exception ex){
            log.error("Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * Gets all places objects information from database
     *
     * @return The list of the Place objects
     */
    public Collection<PlaceDtoResp> getAllPlaces() throws Exception{
        try {
            return placeMapper.placeListToPlaceDtoList(placeRepository.findAll());
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Gets the Place object information from the database by id
     *
     * @param id id of the place object in database
     * @return The Place object from database
     * @throws Exception
     */
    public PlaceDtoResp getPlaceById(Integer id) throws Exception {
        try {
            if (placeRepository.existsById(id))
                return placeMapper.placeToPlaceDto(placeRepository.getReferenceById(id));
            else {
                Exception e = new Exception("The place wasn't found");
                log.error("Error: {}", e.getMessage());
                throw e;
            }
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Removes the row with id from database
     *
     * @param id is a row in database
     */
    public void deletePlaceById(Integer id) throws Exception {
        try {
            if (placeRepository.existsById(id))
                placeRepository.deleteById(id);
            else {
                Exception e = new Exception("The place wasn't found");
                log.error("Error: {}", e.getMessage());
                throw e;
            }
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public Page<PlaceDtoResp> getSortedFilteredPlacesCommon(PlacePage placePage,
                                                           PlaceSearchCriteria placeSearchCriteria)
    throws Exception{
        try {
            return placeCriteriaRepository.findAllWithFilters(placePage, placeSearchCriteria);
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
