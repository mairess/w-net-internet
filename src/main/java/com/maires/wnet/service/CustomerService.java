package com.maires.wnet.service;

import com.maires.wnet.repository.CustomerRepository;
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


}