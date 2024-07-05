package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Person;
import com.maires.wnet.security.Role;

/**
 * The type Person dto.
 */
public record PersonDto(
    Long id,
    String fullName,
    String email,
    String username,
    Role role
) {

  /**
   * From entity person dto.
   *
   * @param person the person
   * @return the person dto
   */
  public static PersonDto fromEntity(Person person) {
    return new PersonDto(
        person.getId(),
        person.getFullName(),
        person.getEmail(),
        person.getUsername(),
        person.getRole()
    );
  }
}