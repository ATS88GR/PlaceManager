package com.education.projects.places.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "place_countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "id", description = "Country id", example = "1")
    @Column(name = "id", insertable = false)
    private Integer id;

    @Schema (name = "countryDescr", description = "Description of the country", example = "professor")
    @Column(name = "countrydescr", nullable = false)
    private String countryDescr;
}
