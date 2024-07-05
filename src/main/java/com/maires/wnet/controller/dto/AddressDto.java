package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Address;

/**
 * The interface Address without id dto.
 */
public record AddressDto(
    String city,
    String state,
    String zipCode,
    String street,
    Integer streetNumber,
    String neighborhood,
    String complement
) {

  /**
   * From entity address dto.
   *
   * @param address the address
   * @return the address dto
   */
  public static AddressDto fromEntity(Address address) {
    return new AddressDto(
        address.getCity(),
        address.getState(),
        address.getZipCode(),
        address.getStreet(),
        address.getStreetNumber(),
        address.getNeighborhood(),
        address.getComplement()
    );
  }

}