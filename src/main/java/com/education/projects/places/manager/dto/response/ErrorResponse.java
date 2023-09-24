package com.education.projects.places.manager.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    @Schema(name = "message",
            description = "Error message",
            example = "User with id 086d792e-7974-4fe4-b2e0-2dba9f79bed8 wasn't found")
    private String message;
}
