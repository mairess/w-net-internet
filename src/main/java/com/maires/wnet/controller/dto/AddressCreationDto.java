package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Address;

/**
 * The type Address creation dto.
 */
public record AddressCreationDto(
    String city,
    String state,
    String zipCode,
    String street,
    Integer streetNumber,
    String neighborhood,
    String complement
) {

  /**
   * To entity address.
   *
   * @return the address
   */
  public Address toEntity() {
    return new Address(
        city,
        state,
        zipCode,
        street,
        streetNumber,
        neighborhood,
        complement
    );
  }

}