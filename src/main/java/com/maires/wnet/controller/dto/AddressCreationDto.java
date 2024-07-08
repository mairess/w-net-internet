package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * The type Address creation dto.
 */
public record AddressCreationDto(
    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    @Pattern(
        regexp = "^[a-zA-ZÀ-ÿ\\s]+$",
        message = "must contain only alphabetic characters and spaces!"
    )
    String city,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    @Pattern(
        regexp = "^[a-zA-Z]+$", message = "must contain only alphabetic characters!"
    )
    String state,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    @Pattern(regexp = "^\\d+$", message = "must contain only digits!")
    String zipCode,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    String street,

    Integer streetNumber,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    String neighborhood,

    String complement
) {

  /**
   * To entity address.
   *
   * @return the address
   */
  public Address toEntity() {
    return new Address(
        city,
        state,
        zipCode,
        street,
        streetNumber,
        neighborhood,
        complement
    );
  }

}