package com.maires.wnet.service;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Equipment;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.service.exception.AddressNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Address service.
 */
@Service
public class AddressService {

  private final AddressRepository addressRepository;
  private final EquipmentRepository equipmentRepository;


  /**
   * Instantiates a new Address service.
   *
   * @param addressRepository   the address repository
   * @param equipmentRepository the equipment repository
   */
  @Autowired
  public AddressService(
      AddressRepository addressRepository,
      EquipmentRepository equipmentRepository
  ) {
    this.addressRepository = addressRepository;
    this.equipmentRepository = equipmentRepository;
  }

  /**
   * Find all addresses list.
   *
   * @return the list
   */
  public List<Address> findAllAddresses() {
    return addressRepository.findAll();
  }

  /**
   * Find address by id address.
   *
   * @param addressId the address id
   * @return the address
   * @throws AddressNotFoundException the address not found exception
   */
  public Address findAddressById(Long addressId) throws AddressNotFoundException {
    return addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);
  }


  /**
   * Remove address by id address.
   *
   * @param addressId the address id
   * @return the address
   * @throws AddressNotFoundException the address not found exception
   */
  @Transactional
  public Address removeAddressById(Long addressId) throws AddressNotFoundException {
    Address deletedAddress = findAddressById(addressId);

    if (deletedAddress.getInstallation() != null) {
      List<Equipment> equipmentList = deletedAddress.getInstallation().getEquipments();

      for (Equipment equipment : equipmentList) {
        equipment.setInstallation(null);
        equipmentRepository.save(equipment);
      }
    }
    addressRepository.delete(deletedAddress);
    return deletedAddress;
  }
}