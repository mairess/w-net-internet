package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.AddressConverter;
import com.maires.wnet.controller.dto.AddressDto;
import com.maires.wnet.controller.dto.RuralAddressCreationDto;
import com.maires.wnet.controller.dto.UrbanAddressCreationDto;
import com.maires.wnet.entity.Address;
import com.maires.wnet.service.AddressService;
import com.maires.wnet.service.exception.AddressNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Address controller.
 */
@RestController
@RequestMapping("/addresses")
public class AddressController {

  private final AddressService addressService;


  /**
   * Instantiates a new Address controller.
   *
   * @param addressService the address service
   */
  @Autowired
  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }


  /**
   * Find all addresses list.
   *
   * @return the list
   */
  @GetMapping
  public List<AddressDto> findAllAddresses() {
    List<Address> allAddresses = addressService.findAllAddresses();

    return allAddresses.stream().map(AddressConverter::returnAddressType).toList();
  }


  /**
   * Find address by id record.
   *
   * @param addressId the address id
   * @return the record
   * @throws AddressNotFoundException the address not found exception
   */
  @GetMapping("/{addressId}")
  public AddressDto findAddressById(@PathVariable Long addressId)
      throws AddressNotFoundException {
    Address address = addressService.removeAddressById(addressId);
    return AddressConverter.returnAddressType(address);
  }


  /**
   * Create rural address rural address dto.
   *
   * @param ruralAddressCreationDto the rural address creation dto
   * @return the rural address dto
   */
  @PostMapping("/rural")
  @ResponseStatus(HttpStatus.CREATED)
  public AddressDto createRuralAddress(
      @RequestBody RuralAddressCreationDto ruralAddressCreationDto) {

    return AddressConverter.returnAddressType(
        addressService.createAddress(ruralAddressCreationDto.toEntity())
    );

  }


  /**
   * Create urban address urban address dto.
   *
   * @param urbanAddressCreationDto the urban address creation dto
   * @return the urban address dto
   */
  @PostMapping("/urban")
  @ResponseStatus(HttpStatus.CREATED)
  public AddressDto createUrbanAddress(
      @RequestBody UrbanAddressCreationDto urbanAddressCreationDto) {

    return AddressConverter.returnAddressType(
        addressService.createAddress(urbanAddressCreationDto.toEntity())
    );
  }


  /**
   * Remove address by id record.
   *
   * @param addressId the address id
   * @return the record
   * @throws AddressNotFoundException the address not found exception
   */
  @DeleteMapping("/{addressId}")
  public AddressDto removeAddressById(@PathVariable Long addressId)
      throws AddressNotFoundException {
    Address address = addressService.removeAddressById(addressId);
    return AddressConverter.returnAddressType(address);
  }

}