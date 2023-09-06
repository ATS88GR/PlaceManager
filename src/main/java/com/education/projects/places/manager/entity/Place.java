package com.education.projects.places.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Places")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(name = "id", description = "Place id", example = "086d792e-7974-4fe4-b2e0-2dba9f79bed8")
    @Column(name = "id", insertable = false)
    private UUID id;

    @Schema(name = "latitude", description = "Place latitude", example = "65.11589054716222")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Schema(name = "longitude", description = "Place longitude", example = "-48.76363334487886")
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Schema(name = "address", description = "Address or plus-code", example = "Grodno, Gaya str, 31 or MRFV+VV3")
    @Column(name = "address", nullable = false)
    private String address;

    @Schema(name = "areaDescr", description = "Description of area", example = "undergrowth")
    @Column(name = "area_descr")
    private String areaDescr;

    @OneToOne(cascade = CascadeType.ALL)
    @Schema(name = "country", description = "Country object")
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
}
