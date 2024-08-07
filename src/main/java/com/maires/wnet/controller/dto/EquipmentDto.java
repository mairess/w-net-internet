package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Equipment;
import com.maires.wnet.entity.EquipmentType;


/**
 * The type Equipment dto.
 */
public record EquipmentDto(
    Long id,
    EquipmentType type,
    String model,
    String serialNumber,
    String manufacturer
) {


  /**
   * From entity equipment dto.
   *
   * @param equipment the equipment
   * @return the equipment dto
   */
  public static EquipmentDto fromEntity(Equipment equipment) {
    return new EquipmentDto(
        equipment.getId(),
        equipment.getType(),
        equipment.getModel(),
        equipment.getSerialNumber(),
        equipment.getManufacturer()
    );
  }

}