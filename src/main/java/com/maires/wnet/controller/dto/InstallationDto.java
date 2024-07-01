package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Equipment;
import com.maires.wnet.entity.Installation;
import java.util.List;


/**
 * The type Installation dto.
 */
public record InstallationDto(
    Long id,
    Long addressId,
    Long planId,
    Long technicianId,
    String installationDate,
    List<Long> equipmentIds) {


  /**
   * From entity installation dto.
   *
   * @param installation the installation
   * @return the installation dto
   */
  public static InstallationDto fromEntity(Installation installation) {
    return new InstallationDto(
        installation.getId(),
        installation.getAddress().getId(),
        installation.getPlan().getId(),
        installation.getTechnician().getId(),
        installation.getInstallationDate(),
        installation.getEquipments().stream().map(Equipment::getId).toList()
    );
  }

}