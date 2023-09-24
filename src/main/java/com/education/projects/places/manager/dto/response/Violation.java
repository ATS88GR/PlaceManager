package com.education.projects.places.manager.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Violation {
    @Schema(name = "fieldName", description = "Violation field name")
    private final String fieldName;
    @Schema(name = "message", description = "Violation message")
    private final String message;
}
