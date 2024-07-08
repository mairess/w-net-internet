package com.maires.wnet.entity;


/**
 * The enum Equipment type.
 */
public enum EquipmentType {

  /**
   * Router equipment type.
   */
  ROUTER("ROUTER"),
  /**
   * Modem equipment type.
   */
  MODEM("MODEM");

  private final String name;

  EquipmentType(String name) {
    this.name = name;
  }

  /**
   * Gets fullName.
   *
   * @return the fullName
   */
  public String getName() {
    return name;
  }
}