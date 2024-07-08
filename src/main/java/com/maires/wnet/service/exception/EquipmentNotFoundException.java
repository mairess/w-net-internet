package com.maires.wnet.service.exception;


/**
 * The type Equipment not found exception.
 */
public class EquipmentNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Equipment not found exception.
   */
  public EquipmentNotFoundException(String identifier) {
    super("Equipment not found with identifier " + identifier + "!");
  }

}