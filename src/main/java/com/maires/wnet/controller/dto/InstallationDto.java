package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Installation;
import java.util.List;


/**
 * The type Installation dto.
 */
public record InstallationDto(
    Long id,
    String installationDate,
    PlanDto plan,
    TechnicianDto technician,
    List<EquipmentDto> equipments
) {


  /**
   * From entity installation dto.
   *
   * @param installation the installation
   * @return the installation dto
   */
  public static InstallationDto fromEntity(Installation installation) {
    return new InstallationDto(
        installation.getId(),
        installation.getInstallationDate(),
        PlanDto.fromEntity(installation.getPlan()),
        TechnicianDto.fromEntity(installation.getTechnician()),
        installation.getEquipments().stream().map(EquipmentDto::fromEntity).toList()
    );
  }

}