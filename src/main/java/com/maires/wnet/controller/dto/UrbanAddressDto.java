package com.maires.wnet.controller.dto;


import com.maires.wnet.entity.UrbanAddress;

/**
 * The addressType Rural address dto.
 */
public record UrbanAddressDto(
    Long id,
    String street,
    int streetNumber,
    String complement,
    String neighborhood,
    String city,
    String state,
    String zipCode
) {

  /**
   * From entity rural address dto.
   *
   * @param urbanAddress the urban address
   * @return the rural address dto
   */
  public static UrbanAddressDto fromEntity(UrbanAddress urbanAddress) {
    return new UrbanAddressDto(
        urbanAddress.getId(),
        urbanAddress.getStreet(),
        urbanAddress.getStreetNumber(),
        urbanAddress.getComplement(),
        urbanAddress.getNeighborhood(),
        urbanAddress.getCity(),
        urbanAddress.getState(),
        urbanAddress.getZipCode()
    );
  }
}