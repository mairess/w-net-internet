package com.maires.wnet.controller.dto;


import com.maires.wnet.entity.UrbanAddress;


/**
 * The type Urban address dto.
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
) implements AddressDto {


  /**
   * From entity urban address dto.
   *
   * @param urbanAddress the urban address
   * @return the urban address dto
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

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public String getCity() {
    return city;
  }

  @Override
  public String getState() {
    return state;
  }

  @Override
  public String getZipCode() {
    return zipCode;
  }
}