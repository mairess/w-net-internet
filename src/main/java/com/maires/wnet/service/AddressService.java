package com.maires.wnet.service;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Installation;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.InstallationRepository;
import com.maires.wnet.service.exception.AddressNotFoundException;
import com.maires.wnet.service.exception.InstallationNotFoundException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * The type Address service.
 */
@Service
public class AddressService {

  private final AddressRepository addressRepository;
  private final InstallationRepository installationRepository;

  /**
   * Instantiates a new Address service.
   *
   * @param addressRepository      the address repository
   * @param installationRepository the installation repository
   */
  @Autowired
  public AddressService(AddressRepository addressRepository,
      InstallationRepository installationRepository) {
    this.addressRepository = addressRepository;
    this.installationRepository = installationRepository;
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
   * Create address t.
   *
   * @param <T>     the type parameter
   * @param address the address
   * @return the t
   */
  public <T extends Address> T createAddress(T address) {
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

  /**
   * Add address installation response entity.
   *
   * @param addressId      the address id
   * @param installationId the installation id
   * @return the response entity
   * @throws AddressNotFoundException      the address not found exception
   * @throws InstallationNotFoundException the installation not found exception
   */
  public ResponseEntity<Map<String, String>> addAddressInstallation(Long addressId,
      Long installationId)
      throws AddressNotFoundException, InstallationNotFoundException {
    Address address = findAddressById(addressId);

    Installation installation = installationRepository
        .findById(installationId)
        .orElseThrow(InstallationNotFoundException::new);

    Map<String, String> response;

    if (address.getInstallation() != null) {
      response = Map.of("message", "This installation already has an association!");
      return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    address.setInstallation(installation);
    installation.setAddress(address);

    addressRepository.save(address);

    response = Map.of("message", "Installation successful associated!");
    return ResponseEntity.ok(response);
  }
}