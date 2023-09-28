package com.education.projects.places.manager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryPage extends CommonPage {

    public CountryPage() {
        super("countryDescr");
    }

    @NotBlank
    @Schema(name = "sortBy",
            description = "Sorting by country description",
            example = "countryDescr")
    private String sortBy = "countryDescr";


}
