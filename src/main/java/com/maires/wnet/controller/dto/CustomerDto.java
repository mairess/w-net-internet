package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Customer;
import java.util.List;


/**
 * The type Customer dto.
 */
public record CustomerDto(
    Long id,
    String fullName,
    String cpf,
    String phone,
    String email,
    String registrationDate,
    List<AddressDto> addresses
) {

  /**
   * From entity customer dto.
   *
   * @param customer the customer
   * @return the customer dto
   */
  public static CustomerDto fromEntity(Customer customer) {
    return new CustomerDto(
        customer.getId(),
        customer.getFullName(),
        customer.getCpf(),
        customer.getPhone(),
        customer.getEmail(),
        customer.getRegistrationDate(),
        customer.getAddresses().stream().map(AddressDto::fromEntity).toList()
    );
  }
}