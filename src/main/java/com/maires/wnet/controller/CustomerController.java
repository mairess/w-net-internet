package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.CustomerCreationDto;
import com.maires.wnet.controller.dto.CustomerDto;
import com.maires.wnet.entity.Customer;
import com.maires.wnet.service.CustomerService;
import com.maires.wnet.service.exception.AddressNotFoundException;
import com.maires.wnet.service.exception.CustomerNotFoundException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Customer controller.
 */
@RestController
@RequestMapping("/customers")
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

  /**
   * Find all customers list.
   *
   * @return the list
   */
  @GetMapping
  public List<CustomerDto> findAllCustomers() {
    return customerService.findAllCustomers().stream().map(CustomerDto::fromEntity).toList();
  }

  /**
   * Find customer by id customer dto.
   *
   * @param customerId the customer id
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  @GetMapping("/{customerId}")
  public CustomerDto findCustomerById(@PathVariable Long customerId)
      throws CustomerNotFoundException {
    return CustomerDto.fromEntity(
        customerService.findCustomerById(customerId)
    );
  }


  /**
   * Create customer customer dto.
   *
   * @param customerCreationDto the customer creation dto
   * @return the customer dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerDto createCustomer(@RequestBody CustomerCreationDto customerCreationDto) {
    Customer newCustomer = customerService.createCustomer(customerCreationDto.toEntity());
    return CustomerDto.fromEntity(newCustomer);
  }


  /**
   * Remove customer by id customer dto.
   *
   * @param customerId the customer id
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  @DeleteMapping("/{customerId}")
  public CustomerDto removeCustomerById(@PathVariable Long customerId)
      throws CustomerNotFoundException {
    return CustomerDto.fromEntity(
        customerService.removeCustomerById(customerId)
    );
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
  @PostMapping("/{customerId}/addresses/{addressId}")
  public ResponseEntity<Map<String, String>> addCustomerAddress(@PathVariable Long customerId,
      @PathVariable Long addressId)
      throws CustomerNotFoundException, AddressNotFoundException {
    return customerService.addCustomerAddress(customerId, addressId);
  }
}