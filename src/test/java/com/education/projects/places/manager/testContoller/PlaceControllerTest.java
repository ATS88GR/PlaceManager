package com.education.projects.places.manager.testContoller;

import com.education.projects.places.manager.dto.request.PlaceDtoReq;
import com.education.projects.places.manager.dto.response.PlaceDtoResp;
import com.education.projects.places.manager.entity.Country;
import com.education.projects.places.manager.entity.Place;
import com.education.projects.places.manager.repository.CountryRepository;
import com.education.projects.places.manager.repository.PlaceRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class PlaceControllerTest {
    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        placeRepository.deleteAll();

        place.setId(null);
        place.setCountry(null);

        place2.setId(null);
        place2.setCountry(null);
    }

    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    CountryRepository countryRepository;

    private final Place place = new Place(
            null,
            BigDecimal.valueOf(53.666692),
            BigDecimal.valueOf(23.821931),
            "MR8C+MQF",
            "undergrowth",
            null);
    private final Place place2 = new Place(
            null,
            BigDecimal.valueOf(53.141355),
            BigDecimal.valueOf(23.163218),
            "45R7+G7W",
            "lea",
            null);

    private final PlaceDtoReq placeDtoReq = new PlaceDtoReq(
            BigDecimal.valueOf(53.666692),
            BigDecimal.valueOf(23.821931),
            "MR8C+MQF",
            "undergrowth",
            null);

    @Test
    void testPostgresIsRunning() {
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void getPlaces() {
        place.setCountry(getCountryByDescr("Belarus"));

        place2.setCountry(getCountryByDescr("Poland"));

        placeRepository.save(place);
        placeRepository.save(place2);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/places")
                .then()
                .log().all()
                .statusCode(200)
                .body(".", hasSize(2));
    }

    @Test
    void createPlace() {
        placeDtoReq.setCountryId(getCountryByDescr("Belarus").getId());

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(placeDtoReq)
                .post("/places")
                .then()
                .log().all()
                .statusCode(201)
                .and()
                .contentType(ContentType.JSON)
                .extract().body().as(PlaceDtoResp.class);
    }

    @Test
    void updatePlace() {
        placeDtoReq.setCountryId(getCountryByDescr("Belarus").getId());

        place.setCountry(getCountryByDescr("Belarus"));

        UUID id = placeRepository.save(place).getId();

        given()
                .contentType(ContentType.JSON)
                .when().body(placeDtoReq)
                .pathParams("id", id)
                .put("/places/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .extract().body().as(PlaceDtoResp.class);
    }

    @Test
    void getPlaceById() {
        place.setCountry(getCountryByDescr("Belarus"));

        place2.setCountry(getCountryByDescr("Poland"));

        placeRepository.save(place);
        UUID idTest = placeRepository.save(place2).getId();

        given()
                .when()
                .pathParams("id", idTest)
                .get("/places/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .extract().body().as(PlaceDtoResp.class);
    }

    @Test
    void deletePlaceById() {
        place.setCountry(getCountryByDescr("Belarus"));

        place2.setCountry(getCountryByDescr("Poland"));

        placeRepository.save(place);
        UUID idTest = placeRepository.save(place2).getId();

        given()
                .when().body(placeDtoReq)
                .pathParams("id", idTest)
                .delete("/places/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .and()
                .contentType(ContentType.TEXT);
    }

    @Test
    void deletePlaceByIdOnFail() {
        place.setCountry(getCountryByDescr("Belarus"));

        place2.setCountry(getCountryByDescr("Poland"));

        placeRepository.save(place);
        UUID idTest = UUID.fromString("4fc4200c-0fd8-409e-9d61-5862d306b4a4");


        given()
                .when().body(placeDtoReq)
                .pathParams("id", idTest)
                .delete("/places/{id}")
                .then()
                .log().all()
                .statusCode(404)
                .and()
                .contentType(ContentType.JSON);
    }

    @Test
    void getSortFilterPlacesWithPagination() {
        place.setCountry(getCountryByDescr("Belarus"));

        place2.setCountry(getCountryByDescr("Poland"));

        placeRepository.save(place);
        placeRepository.save(place2);

        given()
                .contentType(ContentType.JSON)
                .when()
                .queryParam("address", "45R7+G7W")
                .get("/sortedFilteredPlaces")
                .then()
                .log().all()
                .statusCode(200)
                .body("content", hasSize(1));
    }

    private Country getCountryByDescr(String descr) {
        return countryRepository.findAll()
                .stream()
                .filter(country -> country.getCountryDescr().equals(descr))
                .findFirst()
                .get();
    }
}
