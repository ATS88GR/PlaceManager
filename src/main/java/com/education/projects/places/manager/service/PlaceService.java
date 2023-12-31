package com.education.projects.places.manager.service;

import com.education.projects.places.manager.dto.request.PlaceDtoReq;
import com.education.projects.places.manager.dto.response.PlaceDtoResp;
import com.education.projects.places.manager.dto.request.PlacePage;
import com.education.projects.places.manager.dto.request.PlaceSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.UUID;

/**
 * The interface for service Place objects information
 */
public interface PlaceService {

    PlaceDtoResp createPlace(PlaceDtoReq placeDtoReq) throws Exception;

    PlaceDtoResp updatePlace(PlaceDtoReq placeDtoReq, UUID id) throws Exception;

    Collection<PlaceDtoResp> getAllPlaces() throws Exception;

    PlaceDtoResp getPlaceById(UUID id) throws Exception;

    void deletePlaceById(UUID id) throws Exception;

    Page<PlaceDtoResp> getSortFilterPaginPlaces(PlacePage placePage,
                                                PlaceSearchCriteria placeSearchCriteria)
            throws Exception;
}
