package com.education.projects.places.manager.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlaceDtoResp {

    @Schema (name = "latitude", description = "Place latitude", example = "65.11589054716222")
    private BigDecimal latitude;

    @Schema (name = "longitude", description = "Place longitude", example = "-48.76363334487886")
    private BigDecimal longitude;

    @Schema (name = "address", description = "Address or plus-code",
            example = "Grodno, Gaya str, 31 or MRFV+VV3")
    private String address;

    @Schema (name = "areaDescr", description = "Description of area", example = "undergrowth")
    private String areaDescr;

    @Schema (name = "countryId", description = "Country id", example = "3")
    private Integer countryId;
}
