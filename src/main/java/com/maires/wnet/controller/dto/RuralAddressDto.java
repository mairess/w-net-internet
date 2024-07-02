package com.maires.wnet.controller.dto;


import com.maires.wnet.entity.RuralAddress;


/**
 * The type Rural address dto.
 */
public record RuralAddressDto(
    Long id,
    String farmName,
    String village,
    String city,
    String state,
    String zipCode,
    InstallationDto installation
) implements AddressDto {


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
        ruralAddress.getZipCode(),
        ruralAddress.getInstallation() == null ? null
            : InstallationDto.fromEntity(ruralAddress.getInstallation())
    );
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