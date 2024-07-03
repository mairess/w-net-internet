package com.maires.wnet.service;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Customer;
import com.maires.wnet.entity.Equipment;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.CustomerRepository;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.service.exception.AddressNotFoundException;
import com.maires.wnet.service.exception.CustomerNotFoundException;
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
  private final CustomerRepository customerRepository;


  /**
   * Instantiates a new Address service.
   *
   * @param addressRepository   the address repository
   * @param equipmentRepository the equipment repository
   * @param customerRepository  the customer repository
   */
  @Autowired
  public AddressService(
      AddressRepository addressRepository,
      EquipmentRepository equipmentRepository,
      CustomerRepository customerRepository
  ) {
    this.addressRepository = addressRepository;
    this.equipmentRepository = equipmentRepository;
    this.customerRepository = customerRepository;
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
    return addressRepository.findById(addressId)
        .orElseThrow(AddressNotFoundException::new);
  }


  /**
   * Create address.
   *
   * @param address    the address
   * @param customerId the customer id
   * @return the address
   * @throws CustomerNotFoundException the customer not found exception
   */
  public Address createAddress(Address address, Long customerId)
      throws CustomerNotFoundException {

    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(CustomerNotFoundException::new);

    address.setCustomer(customer);

    return addressRepository.save(address);
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