package com.education.projects.places.manager.response.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;


@Data
public class CountryDtoResp {

    @Schema(name = "id", description = "Country id", example = "086d792e-7974-4fe4-b2e0-2dba9f79bed8")
    private UUID id;

    @Schema(name = "countryDescr", description = "Description of the country", example = "Belarus")
    private String countryDescr;
}
