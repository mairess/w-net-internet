package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.User;
import com.maires.wnet.security.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/**
 * The type User creation dto.
 */
public record UserCreationDto(
    @NotNull(message = "cannot be null!")
    @Size(min = 10, max = 50, message = "must be between 10 and 50 characters!")
    @Pattern(
        regexp = "^[a-zA-ZÀ-ÿ\\s]+$",
        message = "must contain only alphabetic characters and spaces!"
    )
    String fullName,

    @NotNull(message = "cannot be null!")
    @Email(message = "must be a valid email address!")
    String email,

    @NotNull(message = "cannot be null!")
    @Size(min = 3, max = 15, message = "must be between 3 and 15 characters!")
    String username,

    @NotNull(message = "cannot be null!")
    @Size(min = 6, max = 50, message = "must be between 6 and 50 characters!")
    String password,

    @NotNull(message = "cannot be null!")
    Role role
) {


  /**
   * To entity user.
   *
   * @return the user
   */
  public User toEntity() {
    return new User(null, fullName, email, username, password, role);
  }
}