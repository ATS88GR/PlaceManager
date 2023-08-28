package com.education.projects.places.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Places")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "id", description = "Place id", example = "1")
    @Column(name = "id", insertable = false)
    private Integer id;

    @Schema (name = "latitude", description = "Place latitude", example = "65.11589054716222")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Schema (name = "longitude", description = "Place longitude", example = "-48.76363334487886")
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Schema (name = "address", description = "Address or plus-code", example = "Grodno, Gaya str, 31 or MRFV+VV3")
    @Column(name = "address", nullable = false)
    private String address;

    @Schema (name = "areaDescr", description = "Description of area", example = "undergrowth")
    @Column(name = "areadescr")
    private String areaDescr;

    @OneToOne(cascade = CascadeType.ALL)
    @Schema (name = "countryId", description = "Country id", example = "3")
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
}
