package com.maires.wnet.utils;

import com.maires.wnet.controller.dto.RuralAddressDto;
import com.maires.wnet.controller.dto.UrbanAddressDto;
import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.RuralAddress;
import com.maires.wnet.entity.UrbanAddress;


/**
 * The type Address util.
 */
public class AddressUtil {


  /**
   * Return address type record.
   *
   * @param address the address
   * @return the record
   */
  public static Record returnAddressType(Address address) {

    if (address instanceof UrbanAddress) {
      return UrbanAddressDto.fromEntity((UrbanAddress) address);
    } else {
      return RuralAddressDto.fromEntity((RuralAddress) address);
    }

  }
}