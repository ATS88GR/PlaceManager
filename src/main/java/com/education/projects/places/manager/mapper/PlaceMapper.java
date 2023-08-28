package com.education.projects.places.manager.mapper;

import com.education.projects.places.manager.dto.request.PlaceDtoReq;
import com.education.projects.places.manager.dto.response.PlaceDtoResp;
import com.education.projects.places.manager.entity.Place;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    PlaceDtoResp placeToPlaceDto(Place place);
    Place placeDtoToPlace(PlaceDtoReq placeDtoReq);
    List<PlaceDtoResp> placeListToPlaceDtoList(List<Place> places);
}
