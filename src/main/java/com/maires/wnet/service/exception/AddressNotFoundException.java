package com.maires.wnet.service.exception;

/**
 * The type Address not found exception.
 */
public class AddressNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Address not found exception.
   */
  public AddressNotFoundException(String identifier) {
    super("Address not found with identifier " + identifier + "!");
  }

}