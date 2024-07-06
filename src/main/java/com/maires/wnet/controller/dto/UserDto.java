package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.User;
import com.maires.wnet.security.Role;

/**
 * The type Person dto.
 */
public record UserDto(
    Long id,
    String fullName,
    String email,
    String username,
    Role role
) {

  /**
   * From entity person dto.
   *
   * @param user the person
   * @return the person dto
   */
  public static UserDto fromEntity(User user) {
    return new UserDto(
        user.getId(),
        user.getFullName(),
        user.getEmail(),
        user.getUsername(),
        user.getRole()
    );
  }
}