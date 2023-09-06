package com.education.projects.places.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "place_countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(name = "id", description = "Country id", example = "086d792e-7974-4fe4-b2e0-2dba9f79bed8")
    @Column(name = "id", insertable = false)
    private UUID id;

    @Schema (name = "countryDescr", description = "Description of the country", example = "Belarus")
    @Column(name = "countrydescr", nullable = false)
    private String countryDescr;
}
