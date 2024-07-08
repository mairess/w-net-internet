package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Equipment;
import com.maires.wnet.entity.EquipmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * The type Equipment creation dto.
 */
public record EquipmentCreationDto(
    @NotNull(message = "cannot be null!")
    EquipmentType type,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    String model,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    String serialNumber,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
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