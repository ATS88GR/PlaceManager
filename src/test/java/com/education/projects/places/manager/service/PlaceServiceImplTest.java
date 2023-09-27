package com.education.projects.places.manager.service;

import com.education.projects.places.manager.dto.request.PlaceDtoReq;
import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.dto.response.PlaceDtoResp;
import com.education.projects.places.manager.entity.Country;
import com.education.projects.places.manager.entity.Place;
import com.education.projects.places.manager.exception.PlaceNotFoundException;
import com.education.projects.places.manager.mapper.PlaceMapper;
import com.education.projects.places.manager.repository.PlaceCriteriaRepository;
import com.education.projects.places.manager.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceServiceImplTest {
    @Mock
    PlaceRepository placeRepository;
    @Mock
    PlaceMapper placeMapper;
    @Mock
    CountryServiceImpl countryService;
    @Mock
    PlaceCriteriaRepository placeCriteriaRepository;
    @InjectMocks
    PlaceServiceImpl placeService;

    /**
     * Test for a successful result of creating a new Place
     *
     * @throws Exception
     */
    @Test
    void createPlace() throws Exception {
        PlaceDtoReq placeDtoReqTest = new PlaceDtoReq(
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"));

        Country countryExp = new Country();
        countryExp.setId(UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"));
        countryExp.setCountryDescr("Belarus");

        Place placeExp = new Place(
                UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4"),
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                countryExp);
        when(countryService.getCountryById(placeDtoReqTest.getCountryId())).thenReturn(countryExp);
        when(placeMapper.placeDtoToPlace(placeDtoReqTest, countryExp)).thenReturn(placeExp);

        Place placeExp2 = new Place(
                UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4"),
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                countryExp);

        CountryDtoResp countryDtoRespExp = new CountryDtoResp();
        countryDtoRespExp.setId(UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"));
        countryDtoRespExp.setCountryDescr("Belarus");

        PlaceDtoResp placeDtoRespExp = new PlaceDtoResp(
                UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4"),
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                countryDtoRespExp);

        countryDtoRespExp.setId(UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"));
        countryDtoRespExp.setCountryDescr("Belarus");

        when(placeRepository.save(placeExp)).thenReturn(placeExp2);
        when(placeMapper.placeToPlaceDto(
                placeExp2,
                new CountryDtoResp(placeExp2.getCountry().getId(), placeExp2.getCountry().getCountryDescr()))).
                thenReturn(placeDtoRespExp);

        PlaceDtoResp placeDtoResp = placeService.createPlace(placeDtoReqTest);

        UUID uuidPlaceExp = UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4");

        assertEquals(uuidPlaceExp, placeDtoResp.getId());
        assertEquals(new BigDecimal("65.11589054716222"), placeDtoResp.getLatitude());
        assertEquals(new BigDecimal("-48.76363334487886"), placeDtoResp.getLongitude());
        assertEquals("Grodno", placeDtoResp.getAddress());
        assertEquals("undergrowth", placeDtoResp.getAreaDescr());
        assertEquals(countryDtoRespExp, placeDtoResp.getCountryDtoResp());
    }

    /**
     * Test for a successful result of updating the Place
     *
     * @throws Exception
     */
    @Test
    void updatePlace() throws Exception {
        UUID uuidPlaceTest = UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4");

        PlaceDtoReq placeDtoReqTest = new PlaceDtoReq(
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"));

        Country countryExp = new Country();
        countryExp.setId(UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"));
        countryExp.setCountryDescr("Belarus");

        Place placeExp = new Place(
                null,
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                countryExp);
        when(placeRepository.existsById(uuidPlaceTest)).thenReturn(true);
        when(countryService.getCountryById(placeDtoReqTest.getCountryId())).thenReturn(countryExp);
        when(placeMapper.placeDtoToPlace(placeDtoReqTest, countryExp)).thenReturn(placeExp);

        placeExp.setId(UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4"));

        Place placeExp2 = new Place(
                UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4"),
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                countryExp);

        CountryDtoResp countryDtoRespExp = new CountryDtoResp();
        countryDtoRespExp.setId(UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"));
        countryDtoRespExp.setCountryDescr("Belarus");

        PlaceDtoResp placeDtoRespExp = new PlaceDtoResp(
                UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4"),
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                countryDtoRespExp);

        countryDtoRespExp.setId(UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"));
        countryDtoRespExp.setCountryDescr("Belarus");

        when(placeRepository.save(placeExp)).thenReturn(placeExp2);
        when(placeMapper.placeToPlaceDto(
                placeExp2,
                new CountryDtoResp(placeExp2.getCountry().getId(), placeExp2.getCountry().getCountryDescr()))).
                thenReturn(placeDtoRespExp);

        PlaceDtoResp placeDtoResp = placeService.updatePlace(placeDtoReqTest, uuidPlaceTest);

        UUID uuidPlaceExp = UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4");

        assertEquals(uuidPlaceExp, placeDtoResp.getId());
        assertEquals(new BigDecimal("65.11589054716222"), placeDtoResp.getLatitude());
        assertEquals(new BigDecimal("-48.76363334487886"), placeDtoResp.getLongitude());
        assertEquals("Grodno", placeDtoResp.getAddress());
        assertEquals("undergrowth", placeDtoResp.getAreaDescr());
        assertEquals(countryDtoRespExp, placeDtoResp.getCountryDtoResp());
    }

    /**
     * Test for an unsuccessful result of updating the Place
     *
     * @throws Exception
     */
    @Test
    void updatePlaceOnFail() {
        UUID uuidPlaceTest = UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4");

        PlaceDtoReq placeDtoReqTest = new PlaceDtoReq(
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"));
        when(placeRepository.existsById(uuidPlaceTest)).thenReturn(false);

        assertThrows(PlaceNotFoundException.class,
                () -> placeService.updatePlace(placeDtoReqTest, uuidPlaceTest));
    }

    /**
     * Test for a successful result of getting all Places
     */
    @Test
    void getAllPlaces() {
        Place placeExp = new Place(
                null,
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                new Country(
                        UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"),
                        "Belarus"));
        Place placeExp2 = new Place(
                null,
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                new Country(
                        UUID.fromString("2842d7f9-83bd-4df7-bcbe-1d66a69dc5e3"),
                        "Poland"));

        List<Place> placeListExp = new ArrayList<>();
        placeListExp.add(placeExp);
        placeListExp.add(placeExp2);

        when(placeRepository.findAll()).thenReturn(placeListExp);

        PlaceDtoResp placeDtoRespExp = new PlaceDtoResp(
                null,
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                new CountryDtoResp(
                        UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"),
                        "Belarus"));
        PlaceDtoResp placeDtoRespExp2 = new PlaceDtoResp(
                null,
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                new CountryDtoResp(
                        UUID.fromString("2842d7f9-83bd-4df7-bcbe-1d66a69dc5e3"),
                        "Poland"));

        List<PlaceDtoResp> placeDtoRespListExp = new ArrayList<>();
        placeDtoRespListExp.add(placeDtoRespExp);
        placeDtoRespListExp.add(placeDtoRespExp2);

        when(placeMapper.placeListToPlaceDtoList(placeListExp)).thenReturn(placeDtoRespListExp);

        PlaceDtoResp placeDtoResp = new PlaceDtoResp(
                null,
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                new CountryDtoResp(
                        UUID.fromString("086d792e-7974-4fe4-b2e0-2dba9f79bed8"),
                        "Belarus"));
        PlaceDtoResp placeDtoResp2 = new PlaceDtoResp(
                null,
                new BigDecimal("65.11589054716222"),
                new BigDecimal("-48.76363334487886"),
                "Grodno",
                "undergrowth",
                new CountryDtoResp(
                        UUID.fromString("2842d7f9-83bd-4df7-bcbe-1d66a69dc5e3"),
                        "Poland"));

        List<PlaceDtoResp> placeDtoRespList = new ArrayList<>();
        placeDtoRespList.add(placeDtoResp);
        placeDtoRespList.add(placeDtoResp2);

        assertEquals(placeDtoRespList, placeService.getAllPlaces());
    }

    @Test
    void getPlacesByIdList() {
    }

    @Test
    void getPlaceById() {
    }

    @Test
    void deletePlaceById() {
    }

    @Test
    void getSortFilterPaginPlaces() {
    }
}