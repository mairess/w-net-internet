package com.maires.wnet.controller.dto;


import java.util.List;


/**
 * The type Installation creation dto.
 */
public record InstallationCreationDto(
    Long planId,
    Long technicianId,
    List<Long> equipmentIds) {

}