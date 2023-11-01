package com.education.projects.places.manager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class CommonPage {

    CommonPage(String sortBy) {
        this.sortBy = sortBy;
    }

    @NotNull
    @Min(value = 0, message = "min value is 0")
    @Schema(name = "pageNumber", description = "Number of page", example = "0")
    private int pageNumber = 0;

    @NotNull
    @Min(value = 1, message = "min value is 1")
    @Max(value = 50, message = "max value is 50")
    @Schema(name = "pageSize", description = "Size of page", example = "5")
    private int pageSize = 5;

    @Schema(name = "sortDirection", description = "Direction of sorting", example = "ASC")
    private Sort.Direction sortDirection = Sort.Direction.ASC;

    @NotBlank
    @Schema(name = "sortBy",
            description = "Sorting by example",
            example = "example")
    private String sortBy = "example";
}
