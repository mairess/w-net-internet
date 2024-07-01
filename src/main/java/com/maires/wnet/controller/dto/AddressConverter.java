package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.RuralAddress;
import com.maires.wnet.entity.UrbanAddress;

/**
 * The type Address converter.
 */
public class AddressConverter {

  /**
   * Return address type address dto.
   *
   * @param address the address
   * @return the address dto
   */
  public static AddressDto returnAddressType(Address address) {

    if (address instanceof UrbanAddress) {
      return UrbanAddressDto.fromEntity((UrbanAddress) address);
    }

    return RuralAddressDto.fromEntity((RuralAddress) address);
  }
}