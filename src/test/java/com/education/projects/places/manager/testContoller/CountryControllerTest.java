package com.education.projects.places.manager.testContoller;

import com.education.projects.places.manager.repository.CountryRepository;
import com.education.projects.places.manager.dto.response.CountryDtoResp;
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

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CountryControllerTest {
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
    }
    @Autowired
    CountryRepository countryRepository;

    @Test
    void getAllCountries() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/countries")
                .then()
                .log().all()
                .statusCode(200)
                .body(".", hasSize(10));
    }
    @Test
    void getSortFilterPaginCountries() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .queryParam("countryDescr", "Belarus")
                .get("/sortedFilteredCountries")
                .then()
                .log().all()
                .statusCode(200)
                .body("content", hasSize(1));
    }
    @Test
    void getCountryById(){
        UUID countryUUIDTest = countryRepository
                .findAll()
                .stream()
                .filter(country -> country.getCountryDescr().equals("Poland"))
                .findFirst()
                .get()
                .getId();

        given()
                .when()
                .pathParams("id", countryUUIDTest)
                .get("/countries/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .extract().body().as(CountryDtoResp.class);

    }
}
