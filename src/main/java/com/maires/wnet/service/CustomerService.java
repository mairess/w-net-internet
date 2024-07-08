package com.maires.wnet.service;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Customer;
import com.maires.wnet.entity.Installation;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.CustomerRepository;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.service.exception.CustomerNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Customer service.
 */
@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final EquipmentRepository equipmentRepository;
  private final AddressRepository addressRepository;


  /**
   * Instantiates a new Customer service.
   *
   * @param customerRepository  the customer repository
   * @param equipmentRepository the equipment repository
   * @param addressRepository   the address repository
   */
  @Autowired
  public CustomerService(
      CustomerRepository customerRepository,
      EquipmentRepository equipmentRepository,
      AddressRepository addressRepository
  ) {
    this.customerRepository = customerRepository;
    this.equipmentRepository = equipmentRepository;
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
        .orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
  }

  /**
   * Find customer addresses list.
   *
   * @param customerId the customer id
   * @return the list
   * @throws CustomerNotFoundException the customer not found exception
   */
  public List<Address> findCustomerAddresses(Long customerId) throws CustomerNotFoundException {

    Customer customer = customerRepository
        .findById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));

    return customer.getAddresses();
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
   * Update customer customer.
   *
   * @param customerId the customer id
   * @param customer   the customer
   * @return the customer
   * @throws CustomerNotFoundException the customer not found exception
   */
  public Customer updateCustomer(Long customerId, Customer customer)
      throws CustomerNotFoundException {
    Customer customerToUpdate = findCustomerById(customerId);

    customerToUpdate.setName(customer.getName());
    customerToUpdate.setCpf(customer.getCpf());
    customerToUpdate.setPhone(customer.getPhone());
    customerToUpdate.setEmail(customer.getEmail());

    return customerRepository.save(customerToUpdate);
  }


  /**
   * Create customer address.
   *
   * @param customerId the customer id
   * @param address    the address
   * @return the address
   * @throws CustomerNotFoundException the customer not found exception
   */
  public Address createCustomerAddress(Long customerId, Address address)
      throws CustomerNotFoundException {

    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));

    address.setCustomer(customer);

    return addressRepository.save(address);
  }

  /**
   * Remove customer by id customer.
   *
   * @param customerId the customer id
   * @return the customer
   * @throws CustomerNotFoundException the customer not found exception
   */
  @Transactional
  public Customer removeCustomerById(Long customerId) throws CustomerNotFoundException {
    Customer deletedCustomer = findCustomerById(customerId);

    deletedCustomer.getAddresses().stream()
        .map(Address::getInstallation)
        .map(Installation::getEquipments)
        .flatMap(List::stream)
        .forEach(equipment -> {
          equipment.setInstallation(null);
          equipmentRepository.save(equipment);
        });

    customerRepository.delete(deletedCustomer);
    return deletedCustomer;
  }
}