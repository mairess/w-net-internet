package com.maires.wnet.service.exception;

/**
 * The type Address already associated exception.
 */
public class AddressAlreadyAssociatedException extends AlreadyAssociatedException {


  /**
   * Instantiates a new Address already associated exception.
   */
  public AddressAlreadyAssociatedException() {
    super("This address is already associated!");
  }

}