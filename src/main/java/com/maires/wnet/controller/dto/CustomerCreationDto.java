package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Customer;

/**
 * The addressType Customer creation dto.
 */
public record CustomerCreationDto(String name, String cpf, String phone, String email) {

  /**
   * To entity customer.
   *
   * @return the customer
   */
  public Customer toEntity() {
    return new Customer(name, cpf, phone, email);
  }
}