package com.maires.wnet.service.exception;


/**
 * The type Technician not found exception.
 */
public class TechnicianNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Technician not found exception.
   */
  public TechnicianNotFoundException(String identifier) {
    super("Technician not found with identifier " + identifier + "!");
  }

}