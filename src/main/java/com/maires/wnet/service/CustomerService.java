package com.maires.wnet.service;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Customer;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.CustomerRepository;
import com.maires.wnet.service.exception.AddressNotFoundException;
import com.maires.wnet.service.exception.CustomerNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * The type Customer service.
 */
@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final AddressRepository addressRepository;

  private final Map<String, String> responseSuccess = Map.of("Message",
      "Address successful associated!");

  private final Map<String, String> responseError = Map.of("Message",
      "This address already has an association!");

  /**
   * Instantiates a new Customer service.
   *
   * @param customerRepository the customer repository
   * @param addressRepository  the address repository
   */
  @Autowired
  public CustomerService(CustomerRepository customerRepository,
      AddressRepository addressRepository) {
    this.customerRepository = customerRepository;
    this.addressRepository = addressRepository;
  }


  /**
   * Find all customers list.
   *
   * @return the list
   */
  public List<Customer> findAllCustomers() {
    return customerRepository.findAll();
  }

  /**
   * Find customer by id customer.
   *
   * @param customerId the customer id
   * @return the customer
   * @throws CustomerNotFoundException the customer not found exception
   */
  public Customer findCustomerById(Long customerId) throws CustomerNotFoundException {
    return customerRepository.findById(customerId)
        .orElseThrow(CustomerNotFoundException::new);
  }

  /**
   * Create customer customer.
   *
   * @param customer the customer
   * @return the customer
   */
  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  /**
   * Remove customer by id customer.
   *
   * @param customerId the customer id
   * @return the customer
   * @throws CustomerNotFoundException the customer not found exception
   */
  public Customer removeCustomerById(Long customerId) throws CustomerNotFoundException {
    Customer deletedCustomer = findCustomerById(customerId);
    customerRepository.delete(deletedCustomer);
    return deletedCustomer;
  }


  /**
   * Add customer address response entity.
   *
   * @param customerId the customer id
   * @param addressId  the address id
   * @return the response entity
   * @throws CustomerNotFoundException the customer not found exception
   * @throws AddressNotFoundException  the address not found exception
   */
  @Transactional
  public ResponseEntity<Map<String, String>> addCustomerAddress(Long customerId, Long addressId)
      throws CustomerNotFoundException, AddressNotFoundException {

    Customer customer = findCustomerById(customerId);
    Address address = addressRepository.findById(addressId)
        .orElseThrow(AddressNotFoundException::new);

    if (address.getCustomer() != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(responseError);
    }

    address.setCustomer(customer);
    customer.getAddresses().add(address);
    addressRepository.save(address);

    return ResponseEntity.ok(responseSuccess);

  }
}