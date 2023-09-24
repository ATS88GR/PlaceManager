package com.education.projects.places.manager.mapper;

import com.education.projects.places.manager.dto.request.PlaceDtoReq;
import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.dto.response.PlaceDtoResp;
import com.education.projects.places.manager.entity.Country;
import com.education.projects.places.manager.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    @Mapping(target = "countryDtoResp", source = "countryDtoRpMap")
    @Mapping(target = "id", expression = "java(place.getId())")
    PlaceDtoResp placeToPlaceDto(Place place, CountryDtoResp countryDtoRpMap);

    @Mapping(target = "country", source = "countryMap")
    @Mapping(target = "id", expression = "java(null)")
    Place placeDtoToPlace(PlaceDtoReq placeDtoReq, Country countryMap);

    List<PlaceDtoResp> placeListToPlaceDtoList(List<Place> places);

    default PlaceDtoResp placeListToPlaceDtoList(Place place) {
        CountryDtoResp countryDtoResp = new CountryDtoResp(
                place.getCountry().getId(), place.getCountry().getCountryDescr());
        PlaceDtoResp placeDtoResp = new PlaceDtoResp();
        placeDtoResp.setId(place.getId());
        placeDtoResp.setLongitude(place.getLongitude());
        placeDtoResp.setLatitude(place.getLatitude());
        placeDtoResp.setAddress(place.getAddress());
        placeDtoResp.setAreaDescr(place.getAreaDescr());
        placeDtoResp.setCountryDtoResp(countryDtoResp);
        return placeDtoResp;
    }
}
