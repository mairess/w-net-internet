package com.maires.wnet.controller;

import com.maires.wnet.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Customer controller.
 */
@RestController
@RequestMapping("customers")
public class CustomerController {

  private final CustomerService customerService;

  /**
   * Instantiates a new Customer controller.
   *
   * @param customerService the customer service
   */
  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

}