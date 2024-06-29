package com.maires.wnet.service;

import com.maires.wnet.entity.Customer;
import com.maires.wnet.repository.CustomerRepository;
import com.maires.wnet.service.exception.CustomerNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Customer service.
 */
@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  /**
   * Instantiates a new Customer service.
   *
   * @param customerRepository the customer repository
   */
  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
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
    Customer customer = findCustomerById(customerId);
    customerRepository.delete(customer);
    return customer;
  }

}