package com.maires.wnet.service.exception;

/**
 * The type Cannot exclude associated entity.
 */
public abstract class CannotBeExcludedException extends Exception {

  /**
   * Instantiates a new Cannot exclude associated entity.
   *
   * @param message the message
   */
  public CannotBeExcludedException(String message) {
    super(message);
  }
}