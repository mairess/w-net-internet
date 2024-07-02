package com.maires.wnet.service.exception;

/**
 * The type Already associated exception.
 */
public abstract class AlreadyAssociatedException extends Exception {

  /**
   * Instantiates a new Already associated exception.
   *
   * @param message the message
   */
  public AlreadyAssociatedException(String message) {
    super(message);
  }
}