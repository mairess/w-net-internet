package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.AddressConverter;
import com.maires.wnet.controller.dto.AddressDto;
import com.maires.wnet.controller.dto.InstallationCreationDto;
import com.maires.wnet.controller.dto.InstallationDto;
import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Installation;
import com.maires.wnet.service.AddressService;
import com.maires.wnet.service.exception.AddressAlreadyAssociatedException;
import com.maires.wnet.service.exception.AddressNotFoundException;
import com.maires.wnet.service.exception.EquipmentAlreadyAssociatedException;
import com.maires.wnet.service.exception.EquipmentNotFoundException;
import com.maires.wnet.service.exception.PlanNotFoundException;
import com.maires.wnet.service.exception.TechnicianNotFoundException;
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
    Address address = addressService.findAddressById(addressId);
    return AddressConverter.returnAddressType(address);
  }

  /**
   * Create address installation installation dto.
   *
   * @param installationCreationDto the installation creation dto
   * @return the installation dto
   * @throws AddressNotFoundException            the address not found exception
   * @throws PlanNotFoundException               the plan not found exception
   * @throws TechnicianNotFoundException         the technician not found exception
   * @throws AddressAlreadyAssociatedException   the address already associated exception
   * @throws EquipmentAlreadyAssociatedException the equipment already associated exception
   * @throws EquipmentNotFoundException          the equipment not found exception
   */
  @PostMapping("/{addressId}/installations")
  @ResponseStatus(HttpStatus.CREATED)
  public InstallationDto createAddressInstallation(
      @PathVariable Long addressId,
      @RequestBody InstallationCreationDto installationCreationDto)
      throws
      AddressAlreadyAssociatedException,
      EquipmentAlreadyAssociatedException,
      AddressNotFoundException,
      EquipmentNotFoundException,
      TechnicianNotFoundException,
      PlanNotFoundException {

    Installation newInstallation = addressService.createAddressInstallation(
        addressId,
        installationCreationDto.planId(),
        installationCreationDto.technicianId(),
        installationCreationDto.equipmentIds()
    );

    return InstallationDto.fromEntity(newInstallation);
  }


  /**
   * Remove address by id address dto.
   *
   * @param addressId the address id
   * @return the address dto
   * @throws AddressNotFoundException the address not found exception
   */
  @DeleteMapping("/{addressId}")
  public AddressDto removeAddressById(@PathVariable Long addressId)
      throws AddressNotFoundException {
    Address address = addressService.removeAddressById(addressId);
    return AddressConverter.returnAddressType(address);
  }

}