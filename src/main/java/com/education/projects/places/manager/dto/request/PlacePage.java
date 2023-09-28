package com.education.projects.places.manager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlacePage extends CommonPage {

    public PlacePage() {
        super("address");
    }

    @NotBlank
    @Schema(name = "sortBy",
            description = "Sorting by address/latitude/longitude",
            example = "address")
    private String sortBy = "address";
}
