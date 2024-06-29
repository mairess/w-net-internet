package com.maires.wnet.controller.dto;


import com.maires.wnet.entity.RuralAddress;

/**
 * The addressType Rural address dto.
 */
public record RuralAddressDto(
    Long id,
    String farmName,
    String village,
    String city,
    String state,
    String zipCode
) {

  /**
   * From entity rural address dto.
   *
   * @param ruralAddress the rural address
   * @return the rural address dto
   */
  public static RuralAddressDto fromEntity(RuralAddress ruralAddress) {
    return new RuralAddressDto(
        ruralAddress.getId(),
        ruralAddress.getFarmName(),
        ruralAddress.getVillage(),
        ruralAddress.getCity(),
        ruralAddress.getState(),
        ruralAddress.getZipCode()
    );
  }
}