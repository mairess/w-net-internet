package com.maires.wnet.security;

/**
 * Enum representing a Role.
 */
public enum Role {
  ADMIN("ADMIN"),
  TECHNICIAN("TECHNICIAN");

  private final String name;

  Role(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}