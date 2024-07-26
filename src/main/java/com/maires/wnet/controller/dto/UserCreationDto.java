package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.User;
import com.maires.wnet.security.Role;
import com.maires.wnet.validation.EnumValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/**
 * The type User creation dto.
 */
public record UserCreationDto(
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
    @Email(message = "must be a valid email address!")
    String email,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    @Size(min = 3, max = 15, message = "must be between 3 and 15 characters!")
    String username,

    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    @Size(min = 6, max = 50, message = "must be between 6 and 50 characters!")
    String password,

    @NotNull(message = "must not be null! Try ADMIN or TECHNICIAN")
    @EnumValidator(enumClazz = Role.class, message = "must be ADMIN or TECHNICIAN")
    String role
) {


  /**
   * To entity user.
   *
   * @return the user
   */
  public User toEntity() {
    return new User(null, fullName, email, username, password, Role.valueOf(role.toUpperCase()));
  }
}