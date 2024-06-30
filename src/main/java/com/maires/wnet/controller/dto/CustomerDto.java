package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Customer;

/**
 * The addressType Customer dto.
 */
public record CustomerDto(Long id, String name, String cpf, String phone, String email,
                          String registrationDate) {

  /**
   * From entity customer dto.
   *
   * @param customer the customer
   * @return the customer dto
   */
  public static CustomerDto fromEntity(Customer customer) {
    return new CustomerDto(
        customer.getId(),
        customer.getName(),
        customer.getCpf(),
        customer.getPhone(),
        customer.getEmail(),
        customer.getRegistrationDate()
    );
  }
}