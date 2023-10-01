package com.education.projects.places.manager.service;

import com.education.projects.places.manager.dto.request.CountryPage;
import com.education.projects.places.manager.dto.request.CountrySearchCriteria;
import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.entity.Country;
import com.education.projects.places.manager.exception.CountryNotFoundException;
import com.education.projects.places.manager.mapper.CountryMapper;
import com.education.projects.places.manager.repository.CountryCriteriaRepository;
import com.education.projects.places.manager.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @Mock
    CountryRepository countryRepository;
    @Mock
    CountryMapper countryMapper;
    @Mock
    CountryCriteriaRepository countryCriteriaRepository;
    @InjectMocks
    CountryServiceImpl countryService;

    /**
     * Test for a successful result of getting all Countries
     */
    @Test
    void getAllCountries() {
        List<CountryDtoResp> countryRespExp = new ArrayList<>();
        countryRespExp.add(
                new CountryDtoResp(UUID.fromString("0994da2b-8439-4843-80e6-69ee7489a3b7"),
                        "Belarus"));
        countryRespExp.add(
                new CountryDtoResp(UUID.fromString("7287e720-6507-4349-893f-42b41ac5b4ac"),
                        "Poland"));

        List<CountryDtoResp> countryResp = new ArrayList<>();
        countryResp.add(
                new CountryDtoResp(UUID.fromString("0994da2b-8439-4843-80e6-69ee7489a3b7"),
                        "Belarus"));
        countryResp.add(
                new CountryDtoResp(UUID.fromString("7287e720-6507-4349-893f-42b41ac5b4ac"),
                        "Poland"));

        List<Country> countryList = new ArrayList<>();
        countryList.add(
                new Country(UUID.fromString("0994da2b-8439-4843-80e6-69ee7489a3b7"),
                        "Belarus"));
        countryList.add(
                new Country(UUID.fromString("7287e720-6507-4349-893f-42b41ac5b4ac"),
                        "Poland"));

        when(countryRepository.findAll()).thenReturn(countryList);
        when(countryMapper.countryListToCountryDtoList(countryList)).thenReturn(countryResp);

        assertEquals(countryRespExp, countryService.getAllCountries());

    }

    /**
     * Test for a successful result of getting the Country DTO by Id
     *
     * @throws Exception
     */
    @Test
    void getCountryDtoById() throws Exception {
        UUID testUUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

        CountryDtoResp countryDtoRespExp = new CountryDtoResp();
        countryDtoRespExp.setId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        countryDtoRespExp.setCountryDescr("Belarus");

        Country country = new Country();
        country.setId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        country.setCountryDescr("Belarus");

        UUID expectedUUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        String expectedCountryDescr = "Belarus";

        when(countryRepository.existsById(testUUID)).thenReturn(true);
        when(countryRepository.getReferenceById(testUUID)).thenReturn(country);
        when(countryMapper.countryToCountryDto(country)).thenReturn(countryDtoRespExp);
        CountryDtoResp testResult = countryService.getCountryDtoById(testUUID);

        verify(countryRepository).existsById(testUUID);
        verify(countryRepository).getReferenceById(testUUID);
        verify(countryMapper).countryToCountryDto(any());

        assertEquals(expectedUUID, testResult.getId());
        assertEquals(expectedCountryDescr, testResult.getCountryDescr());
    }

    /**
     * Test for an unsuccessful result of getting the Country DTO by Id
     */
    @Test
    void getCountryDtoByIdOnFail() {
        UUID testUUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

        when(countryRepository.existsById(testUUID)).thenReturn(false);

        assertThrows(CountryNotFoundException.class, () -> countryService.getCountryDtoById(testUUID));
    }

    /**
     * Test for a successful result of getting the Country by Id
     *
     * @throws Exception
     */
    @Test
    void getCountryById() throws Exception {
        UUID testUUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

        Country country = new Country();
        country.setId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        country.setCountryDescr("Belarus");

        UUID expectedUUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        String expectedCountry = "Belarus";

        when(countryRepository.existsById(testUUID)).thenReturn(true);
        when(countryRepository.getReferenceById(testUUID)).thenReturn(country);
        Country testResult = countryService.getCountryById(testUUID);

        assertEquals(expectedUUID, testResult.getId());
        assertEquals(expectedCountry, testResult.getCountryDescr());
    }

    /**
     * Test for an unsuccessful result of getting the Country by Id
     */
    @Test
    void getCountryByIdOnFail() {
        UUID testUUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

        when(countryRepository.existsById(testUUID)).thenReturn(false);

        assertThrows(CountryNotFoundException.class, () -> countryService.getCountryById(testUUID));
    }

    @Test
    void getSortFilterPaginCountries() {
        Page<CountryDtoResp> pageCountryDtoRespExp = mock(Page.class);

        CountryPage countryPage = new CountryPage();
        countryPage.setPageNumber(0);
        countryPage.setSortBy(String.valueOf(Sort.Direction.ASC));
        countryPage.setPageSize(5);
        countryPage.setSortBy("countryDescr");

        CountrySearchCriteria countrySearchCriteria = new CountrySearchCriteria();
        countrySearchCriteria.setCountryDescr("Belarus");

        when(countryCriteriaRepository.findAllWithFilters(
                countryPage, countrySearchCriteria)).thenReturn(pageCountryDtoRespExp);

        assertEquals(pageCountryDtoRespExp, countryService.getSortFilterPaginCountries(
                countryPage, countrySearchCriteria));
    }
}