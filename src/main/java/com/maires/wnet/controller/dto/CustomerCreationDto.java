package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * The type Customer creation dto.
 */
public record CustomerCreationDto(
    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    @Size(min = 10, max = 50, message = "must be between 10 and 50 characters!")
    @Pattern(
        regexp = "^[a-zA-ZÀ-ÿ\\s]+$",
        message = "must contain only alphabetic characters and spaces!"
    )
    String fullName,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    @Pattern(regexp = "^\\d+$", message = "must contain only digits!")
    String cpf,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    @Pattern(regexp = "^\\d+$", message = "must contain only digits!")
    String phone,

    @NotNull(message = "cannot be null!")
    @Email(message = "must be a valid email address!")
    String email
) {

  /**
   * To entity customer.
   *
   * @return the customer
   */
  public Customer toEntity() {
    return new Customer(fullName, cpf, phone, email);
  }
}