package com.maires.wnet.security;

/**
 * Enum representing a Role.
 */
public enum Role {
  ADMIN("ADMIN"),
  TECHNICIAN("TECHNICIAN"),
  USER("USER");

  private final String name;

  Role(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}