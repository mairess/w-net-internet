package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.RuralAddress;


/**
 * The type Rural address creation dto.
 */
public record RuralAddressCreationDto(
    String city,
    String state,
    String zipCode,
    String farmName,
    String village) {


  /**
   * To entity rural address.
   *
   * @return the rural address
   */
  public RuralAddress toEntity() {
    return new RuralAddress(city, state, zipCode, farmName, village);
  }

}