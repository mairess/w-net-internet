package com.maires.wnet.service.exception;

/**
 * The type Person not found exception.
 */
public class UserNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Person not found exception.
   */
  public UserNotFoundException(String identifier) {
    super("User not found with identifier " + identifier + "!");
  }

}