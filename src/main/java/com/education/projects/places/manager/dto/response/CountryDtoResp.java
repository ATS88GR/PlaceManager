package com.education.projects.places.manager.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CountryDtoResp {

    @Schema(name = "id", description = "Country id", example = "1")
    private Integer id;

    @Schema (name = "countryDescr", description = "Description of the country", example = "Belarus")
    private String countryDescr;
}
