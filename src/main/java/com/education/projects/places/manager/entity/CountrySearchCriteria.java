package com.education.projects.places.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountrySearchCriteria {

    @Schema(name = "countryDescr", description = "Country name", example = "Belarus")
    @NotBlank(message = "country description should not be blank")
    @Column(name = "countryDescr", nullable = false)
    private String countryDescr;
}
