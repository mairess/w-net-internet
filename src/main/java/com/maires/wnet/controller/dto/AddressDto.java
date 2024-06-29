package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.RuralAddress;
import com.maires.wnet.entity.UrbanAddress;

/**
 * The addressType Address dto.
 */
public record AddressDto(
    Long id,
    String city,
    String state,
    String zipCode,
    String addressType,
    String farmName,
    String village,
    String street,
    int streetNumber,
    String complement,
    String neighborhood
) {

  /**
   * From entity address dto.
   *
   * @param address the address
   * @return the address dto
   */
  public static AddressDto fromEntity(Address address) {
    AddressDto addressDto = new AddressDto(
        address.getId(),
        address.getCity(),
        address.getState(),
        address.getZipCode(),
        null, // Vamos definir o type mais tarde
        null, // farmName
        null, // village
        null, // street
        0,    // streetNumber
        null, // complement
        null  // neighborhood
    );

    if (address instanceof RuralAddress ruralAddress) {
      addressDto = new AddressDto(
          ruralAddress.getId(),
          ruralAddress.getCity(),
          ruralAddress.getState(),
          ruralAddress.getZipCode(),
          "rural",
          ruralAddress.getFarmName(),
          ruralAddress.getVillage(),
          null,
          0,
          null,
          null
      );
    } else if (address instanceof UrbanAddress urbanAddress) {
      addressDto = new AddressDto(
          urbanAddress.getId(),
          urbanAddress.getCity(),
          urbanAddress.getState(),
          urbanAddress.getZipCode(),
          "urban",
          null,
          null,
          urbanAddress.getStreet(),
          urbanAddress.getStreetNumber(),
          urbanAddress.getComplement(),
          urbanAddress.getNeighborhood()
      );
    } else {
      throw new IllegalArgumentException("Unsupported address type");
    }
    return addressDto;

  }
}