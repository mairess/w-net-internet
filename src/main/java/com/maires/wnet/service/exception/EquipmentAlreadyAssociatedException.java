package com.maires.wnet.service.exception;

/**
 * The type Equipment already associated exception.
 */
public class EquipmentAlreadyAssociatedException extends AlreadyAssociatedException {

  /**
   * Instantiates a new Equipment already associated exception.
   */
  public EquipmentAlreadyAssociatedException() {
    super("This equipment is already associated!");
  }

}