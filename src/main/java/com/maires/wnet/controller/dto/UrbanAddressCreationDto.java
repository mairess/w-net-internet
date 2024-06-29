package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.UrbanAddress;

/**
 * The addressType Rural address creation dto.
 */
public record UrbanAddressCreationDto(
    String city,
    String state,
    String zipCode,
    String street,
    int streetNumber,
    String complement,
    String neighborhood
) {

  /**
   * To entity rural address.
   *
   * @return the rural address
   */
  public UrbanAddress toEntity() {
    return new UrbanAddress(city, state, zipCode, street, streetNumber, complement, neighborhood);
  }

}