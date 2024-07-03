package com.maires.wnet.service.exception;

/**
 * The type Equipment cannot be excluded exception.
 */
public class EquipmentCannotBeExcludedException extends CannotBeExcludedException {

  /**
   * Instantiates a new Equipment cannot be excluded exception.
   */
  public EquipmentCannotBeExcludedException() {
    super("This equipment cannot be excluded because it has an association!");
  }


}