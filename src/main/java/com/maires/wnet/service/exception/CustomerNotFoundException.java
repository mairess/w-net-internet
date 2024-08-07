package com.maires.wnet.service.exception;

/**
 * The type Customer not found exception.
 */
public class CustomerNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Customer not found exception.
   */
  public CustomerNotFoundException(String identifier) {
    super("Customer not found with identifier " + identifier + "!");
  }

}