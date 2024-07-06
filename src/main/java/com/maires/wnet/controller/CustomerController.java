package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.AddressCreationDto;
import com.maires.wnet.controller.dto.AddressDto;
import com.maires.wnet.controller.dto.CustomerCreationDto;
import com.maires.wnet.controller.dto.CustomerDto;
import com.maires.wnet.entity.Customer;
import com.maires.wnet.service.CustomerService;
import com.maires.wnet.service.exception.CustomerNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public List<CustomerDto> findAllCustomers() {
    return customerService.findAllCustomers()
        .stream().map(CustomerDto::fromEntity).toList();
  }

  /**
   * Find customer by id customer dto.
   *
   * @param customerId the customer id
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  @GetMapping("/{customerId}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public CustomerDto findCustomerById(@PathVariable Long customerId)
      throws CustomerNotFoundException {
    return CustomerDto.fromEntity(customerService.findCustomerById(customerId));
  }

  /**
   * Find customer addresses list.
   *
   * @param customerId the customer id
   * @return the list
   * @throws CustomerNotFoundException the customer not found exception
   */
  @GetMapping("/{customerId}/addresses")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public List<AddressDto> findCustomerAddresses(@PathVariable Long customerId)
      throws CustomerNotFoundException {
    return customerService.findCustomerAddresses(customerId).stream().map(AddressDto::fromEntity)
        .toList();
  }


  /**
   * Create customer customer dto.
   *
   * @param customerCreationDto the customer creation dto
   * @return the customer dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public CustomerDto createCustomer(
      @RequestBody CustomerCreationDto customerCreationDto
  ) {
    return CustomerDto.fromEntity(customerService.createCustomer(customerCreationDto.toEntity()));
  }


  /**
   * Update customer customer dto.
   *
   * @param customerId the customer id
   * @param customer   the customer
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  @PutMapping("/{customerId}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public CustomerDto updateCustomer(
      @PathVariable Long customerId,
      @RequestBody Customer customer
  ) throws CustomerNotFoundException {
    return CustomerDto.fromEntity(customerService.updateCustomer(customerId, customer));
  }


  /**
   * Create customer address address dto.
   *
   * @param customerId         the customer id
   * @param addressCreationDto the address creation dto
   * @return the address dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  @PostMapping("/{customerId}/addresses")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public AddressDto createCustomerAddress(
      @PathVariable Long customerId,
      @RequestBody AddressCreationDto addressCreationDto
  ) throws CustomerNotFoundException {

    return AddressDto.fromEntity(
        customerService.createCustomerAddress(customerId, addressCreationDto.toEntity()));
  }

  /**
   * Remove customer by id customer dto.
   *
   * @param customerId the customer id
   * @return the customer dto
   * @throws CustomerNotFoundException the customer not found exception
   */
  @DeleteMapping("/{customerId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CustomerDto removeCustomerById(@PathVariable Long customerId)
      throws CustomerNotFoundException {
    return CustomerDto.fromEntity(customerService.removeCustomerById(customerId));
  }
}