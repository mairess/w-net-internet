package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.AddressCreationDto;
import com.maires.wnet.controller.dto.AddressDto;
import com.maires.wnet.controller.dto.InstallationCreationDto;
import com.maires.wnet.controller.dto.InstallationDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    return addressService.findAllAddresses().stream().map(AddressDto::fromEntity).toList();
  }


  /**
   * Find address by id address dto.
   *
   * @param addressId the address id
   * @return the address dto
   * @throws AddressNotFoundException the address not found exception
   */
  @GetMapping("/{addressId}")
  public AddressDto findAddressById(@PathVariable Long addressId)
      throws AddressNotFoundException {
    return AddressDto.fromEntity(addressService.findAddressById(addressId));
  }


  /**
   * Create address installation installation dto.
   *
   * @param addressId               the address id
   * @param installationCreationDto the installation creation dto
   * @return the installation dto
   * @throws AddressAlreadyAssociatedException   the address already associated exception
   * @throws EquipmentAlreadyAssociatedException the equipment already associated exception
   * @throws AddressNotFoundException            the address not found exception
   * @throws EquipmentNotFoundException          the equipment not found exception
   * @throws TechnicianNotFoundException         the technician not found exception
   * @throws PlanNotFoundException               the plan not found exception
   */
  @PostMapping("/{addressId}/installations")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
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
   * Update address dto.
   *
   * @param addressId          the address id
   * @param addressCreationDto the address creation dto
   * @return the address dto
   * @throws AddressNotFoundException the address not found exception
   */
  @PutMapping("/{addressId}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public AddressDto updateAddress(
      @PathVariable Long addressId,
      @RequestBody AddressCreationDto addressCreationDto
  ) throws AddressNotFoundException {
    return AddressDto.fromEntity(
        addressService.updateAddress(addressId, addressCreationDto.toEntity()));
  }


  /**
   * Remove address by id address dto.
   *
   * @param addressId the address id
   * @return the address dto
   * @throws AddressNotFoundException the address not found exception
   */
  @DeleteMapping("/{addressId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public AddressDto removeAddressById(@PathVariable Long addressId)
      throws AddressNotFoundException {
    return AddressDto.fromEntity(addressService.removeAddressById(addressId));
  }

}