package com.education.projects.places.manager.service;

import com.education.projects.places.manager.dto.request.PlaceDtoReq;
import com.education.projects.places.manager.dto.response.PlaceDtoResp;
import com.education.projects.places.manager.entity.Place;

import java.util.Collection;

/**
 * The interface for service Place objects information
 */
public interface PlaceService {

    PlaceDtoResp createPlace(PlaceDtoReq placeDtoReq) throws Exception;

    PlaceDtoResp updatePlace(PlaceDtoReq placeDtoReq, Integer id) throws Exception;

    Collection<PlaceDtoResp> getAllPlaces() throws Exception;

    PlaceDtoResp getPlaceById(Integer id) throws Exception;

    void deletePlaceById(Integer id) throws Exception;


}
