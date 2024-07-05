package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Person;
import com.maires.wnet.security.Role;

/**
 * The type Person creation dto.
 */
public record PersonCreationDto(
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
  public Person toEntity() {
    return new Person(null, fullName, email, username, password, role);
  }
}