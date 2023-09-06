package com.education.projects.places.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceSearchCriteria {

    @Schema(name = "address", description = "Address or plus-code", example = "Grodno, Gaya str, 31 or MRFV+VV3")
    @NotBlank(message = "address should not be blank")
    @Column(name = "address", nullable = false)
    private String address;
}
