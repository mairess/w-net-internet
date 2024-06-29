package com.maires.wnet.service.exceptions;


/**
 * The type Technician not found exception.
 */
public class TechnicianNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Technician not found exception.
   */
  public TechnicianNotFoundException() {
    super("Technician not found!");
  }

}