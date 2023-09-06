package com.education.projects.places.manager.service;

import com.education.projects.places.manager.exception.EmptyException;
import com.education.projects.places.manager.exception.PlaceNotFoundException;
import com.education.projects.places.manager.request.dto.PlaceDtoReq;
import com.education.projects.places.manager.response.dto.PlaceDtoResp;
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
import java.util.UUID;

/**
 * The class for service Place information in database
 */
@Service
@Slf4j
public class PlaceServiceImpl implements PlaceService {

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
        Place place = placeMapper.placeDtoToPlace(placeDtoReq,
                countryServiceImpl.getCountryById(placeDtoReq.getCountryId()));
        return placeMapper.placeToPlaceDto(placeRepository.save(place));
    }

    /**
     * Updates the Place object information by id with update place information
     *
     * @param placeDtoReq Place object information to update
     * @param id          id of the place object to be updated
     * @return Place object information from database by id
     * @throws Exception
     */
    public PlaceDtoResp updatePlace(PlaceDtoReq placeDtoReq, UUID id) throws Exception {
        if (placeRepository.existsById(id)) {
            Place placeToChange = placeMapper.placeDtoToPlace(placeDtoReq,
                    countryServiceImpl.getCountryById(placeDtoReq.getCountryId()));
            placeToChange.setId(id);
            return placeMapper.placeToPlaceDto(placeRepository.save(placeToChange));
        }
        throw new PlaceNotFoundException(id);
    }

    /**
     * Gets all places objects information from database
     *
     * @return The list of the Place objects
     */
    public Collection<PlaceDtoResp> getAllPlaces() throws Exception {
        Collection<PlaceDtoResp> placeDtoRespList =
                placeMapper.placeListToPlaceDtoList(placeRepository.findAll());
        if (placeDtoRespList.isEmpty()) throw new EmptyException();
        return placeMapper.placeListToPlaceDtoList(placeRepository.findAll());
    }

    /**
     * Gets the Place object information from the database by id
     *
     * @param id id of the place object in database
     * @return The Place object from database
     * @throws Exception
     */
    public PlaceDtoResp getPlaceById(UUID id) throws Exception {
        if (placeRepository.existsById(id))
            return placeMapper.placeToPlaceDto(placeRepository.getReferenceById(id));
        throw new PlaceNotFoundException(id);
    }

    /**
     * Removes the row with id from database
     *
     * @param id is a row in database
     */
    public void deletePlaceById(UUID id) throws Exception {
        if (placeRepository.existsById(id))
            placeRepository.deleteById(id);
        throw new PlaceNotFoundException(id);
    }

    /**
     * @param placePage           is an object with pagination settings
     * @param placeSearchCriteria is an object with search settings
     * @return List of users with pagination and search settings
     * @throws Exception
     */
    public Page<PlaceDtoResp> getSortFilterPaginPlaces(PlacePage placePage,
                                                       PlaceSearchCriteria placeSearchCriteria)
            throws Exception {
        Page<PlaceDtoResp> placeDtoResp =
                placeCriteriaRepository.findAllWithFilters(placePage, placeSearchCriteria);
        if (placeDtoResp.isEmpty()) throw new EmptyException();
        return placeDtoResp;
    }
}
