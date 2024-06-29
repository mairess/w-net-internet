package com.maires.wnet.service;

import com.maires.wnet.entity.Address;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.service.exception.AddressNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Address service.
 */
@Service
public class AddressService {

  private final AddressRepository addressRepository;

  /**
   * Instantiates a new Address service.
   *
   * @param addressRepository the address repository
   */
  @Autowired
  public AddressService(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
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
   * @param address the address
   * @return the address
   */
  public Address createAddress(Address address) {
    return addressRepository.save(address);
  }

  /**
   * Remove address by id address.
   *
   * @param addressId the address id
   * @return the address
   * @throws AddressNotFoundException the address not found exception
   */
  public Address removeAddressById(Long addressId) throws AddressNotFoundException {
    Address deletedAddress = findAddressById(addressId);
    addressRepository.delete(deletedAddress);
    return deletedAddress;
  }
}