package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Equipment;

/**
 * The type Equipment creation dto.
 */
public record EquipmentCreationDto(
    String type,
    String model,
    String serialNumber,
    String manufacturer
) {

  /**
   * To entity equipment.
   *
   * @return the equipment
   */
  public Equipment toEntity() {
    return new Equipment(type, model, serialNumber, manufacturer);
  }

}