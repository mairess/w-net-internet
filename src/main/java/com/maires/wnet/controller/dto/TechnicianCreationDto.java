package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Technician;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * The type Technician creation dto.
 */
public record TechnicianCreationDto(
    @NotNull(message = "cannot be null!")
    @Size(min = 10, max = 50, message = "must be between 10 and 50 characters!")
    @Pattern(
        regexp = "^[a-zA-ZÀ-ÿ\\s]+$",
        message = "must contain only alphabetic characters and spaces!"
    )
    String fullName,

    @NotNull(message = "cannot be null!")
    String phone,

    @NotNull(message = "cannot be null!")
    @Email(message = "must be a valid email address!")
    String email
) {

  /**
   * To entity technician.
   *
   * @return the technician
   */
  public Technician toEntity() {
    return new Technician(fullName, phone, email);
  }

}