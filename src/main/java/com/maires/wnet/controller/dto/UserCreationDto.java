package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.User;
import com.maires.wnet.security.Role;

/**
 * The type Person creation dto.
 */
public record UserCreationDto(
    String fullName,
    String email,
    String username,
    String password,
    Role role
) {

  /**
   * To entity person.
   *
   * @return the person
   */
  public User toEntity() {
    return new User(null, fullName, email, username, password, role);
  }
}