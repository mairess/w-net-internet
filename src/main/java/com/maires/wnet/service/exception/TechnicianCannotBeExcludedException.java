package com.maires.wnet.service.exception;

/**
 * The type Technician cannot be excluded exception.
 */
public class TechnicianCannotBeExcludedException extends CannotBeExcludedException {

  /**
   * Instantiates a new Technician cannot be excluded exception.
   */
  public TechnicianCannotBeExcludedException() {
    super("This Technician cannot be excluded because he has an association!");
  }
}