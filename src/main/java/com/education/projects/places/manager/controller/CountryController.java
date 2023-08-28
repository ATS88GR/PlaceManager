package com.education.projects.places.manager.controller;

import com.education.projects.places.manager.dto.response.CountryDtoResp;
import com.education.projects.places.manager.service.CountryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Validated
@Slf4j
@Tag(name = "Countries API")
public class CountryController {
    @Autowired
    private CountryServiceImpl countryServiceImpl;

    @Operation(summary = "Gets information about all countries from database",
            description = "Returns collection of country objects from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @GetMapping("/countries")
    public ResponseEntity<Collection<CountryDtoResp>> getCountries() throws Exception {
        log.info("Get all countries info");
        return new ResponseEntity <> (countryServiceImpl.getAllCountries(), HttpStatus.OK);
    }

    @Operation(summary = "Gets country by id",
            description = "Returns id country information from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found - The country was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @GetMapping("/countries/{id}")
    public ResponseEntity<CountryDtoResp> getCountryById(@PathVariable("id") @NotNull @Min(1) Integer id)
            throws Exception{
        log.info("Gets country with id = {}", id);
        return new ResponseEntity <> (countryServiceImpl.getCountryDtoById(id), HttpStatus.OK);
    }
}
