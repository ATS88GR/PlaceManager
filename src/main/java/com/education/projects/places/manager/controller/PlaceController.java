package com.education.projects.places.manager.controller;

import com.education.projects.places.manager.dto.request.PlaceDtoReq;
import com.education.projects.places.manager.dto.request.PlacePage;
import com.education.projects.places.manager.dto.request.PlaceSearchCriteria;
import com.education.projects.places.manager.dto.response.PlaceDtoResp;
import com.education.projects.places.manager.service.PlaceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@RestController
@Slf4j
@Tag(name = "Places API")
public class PlaceController {

    @Autowired
    private PlaceServiceImpl placeServiceImpl;

    @Operation(summary = "Creates new row in database with place information",
            description = "Returns created place information from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @PostMapping("/places")  //url
    public ResponseEntity<PlaceDtoResp> createPlace(@Valid @RequestBody PlaceDtoReq placeDtoReq)
            throws Exception {
        log.info("Create place = {}", placeDtoReq);
        return new ResponseEntity<>(placeServiceImpl.createPlace(placeDtoReq), HttpStatus.CREATED);
    }

    @Operation(summary = "Updates place information by id",
            description = "Returns updated place information from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The place was not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @PutMapping("/places/{id}")
    public ResponseEntity<PlaceDtoResp> updatePlace(
            @Valid @RequestBody PlaceDtoReq placeDtoReq,
            @PathVariable("id") UUID id)
            throws Exception {
        log.info("Update place with id = {}, update place info {}", id, placeDtoReq);
        return new ResponseEntity<>(placeServiceImpl.updatePlace(placeDtoReq, id), HttpStatus.OK);
    }

    @Operation(summary = "Gets information about all places from database",
            description = "Returns collection of place objects from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @GetMapping("/places")
    public ResponseEntity<Collection<PlaceDtoResp>> getPlaces() {
        log.info("Get all place info");
        return new ResponseEntity<>(placeServiceImpl.getAllPlaces(), HttpStatus.OK);
    }

    @Operation(summary = "Gets information about places from database by Id list",
            description = "Returns collection of place objects from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @GetMapping(value = "/placesByIdList")
    public ResponseEntity<Collection<PlaceDtoResp>> getPlacesByIdList(
            @RequestParam Set<UUID> uuidSet) {
        log.info("Get place info by Id list");
        return new ResponseEntity<>(placeServiceImpl.getPlacesByIdList(uuidSet), HttpStatus.OK);
    }

    @Operation(summary = "Gets sorted and filtered information about places from database",
            description = "Returns collection of place objects from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @GetMapping("/sortedFilteredPlaces")
    public ResponseEntity<Page<PlaceDtoResp>> getSortFilterPlacesCommon(
            @Valid PlacePage placePage,
            @Valid PlaceSearchCriteria placeSearchCriteria) {
        log.info("Get common sorted and filtered place info");
        return new ResponseEntity<>(placeServiceImpl.getSortFilterPaginPlaces(placePage, placeSearchCriteria),
                HttpStatus.OK);
    }

    @Operation(summary = "Gets place by id",
            description = "Returns id place information from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found - The place was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @GetMapping("/places/{id}")
    public ResponseEntity<PlaceDtoResp> getPlaceById(
            @PathVariable("id") UUID id)
            throws Exception {
        log.info("Gets place with id = {}", id);
        return new ResponseEntity<>(placeServiceImpl.getPlaceById(id), HttpStatus.OK);
    }

    @Operation(summary = "Deletes place by id",
            description = "Removes the row with id from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found - The place was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server error")
    })
    @DeleteMapping("/places/{id}")
    public ResponseEntity<String> deletePlaceById(
            @PathVariable("id") UUID id)
            throws Exception {
        log.info("Deletes place with id = {}", id);
        placeServiceImpl.deletePlaceById(id);
        return new ResponseEntity<>("The place deleted", HttpStatus.OK);
    }
}
