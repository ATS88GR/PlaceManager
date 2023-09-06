package com.education.projects.places.manager.mapper;

import com.education.projects.places.manager.entity.Country;
import com.education.projects.places.manager.request.dto.PlaceDtoReq;
import com.education.projects.places.manager.response.dto.PlaceDtoResp;
import com.education.projects.places.manager.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    PlaceDtoResp placeToPlaceDto(Place place);
    @Mapping(target = "country", expression = "java(countryMap)")
    @Mapping(target = "id", expression = "java(null)")
    Place placeDtoToPlace(PlaceDtoReq placeDtoReq, Country countryMap);
    List<PlaceDtoResp> placeListToPlaceDtoList(List<Place> places);
}
