package com.maires.wnet.controller.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;


/**
 * The type Installation creation dto.
 */
public record InstallationCreationDto(
    @NotNull(message = "cannot be null!")
    @Pattern(regexp = "^\\d+$", message = "must contain only digits!")
    Long planId,

    @NotNull(message = "cannot be null!")
    @Pattern(regexp = "^\\d+$", message = "must contain only digits!")
    Long technicianId,

    List<Long> equipmentIds
) {

}