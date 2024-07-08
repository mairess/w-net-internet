package com.maires.wnet.service.exception;


/**
 * The type Pan not found exception.
 */
public class PlanNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Pan not found exception.
   */
  public PlanNotFoundException(String identifier) {
    super("Plan not found with identifier " + identifier + "!");
  }

}