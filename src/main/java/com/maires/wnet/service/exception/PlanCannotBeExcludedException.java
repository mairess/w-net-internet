package com.maires.wnet.service.exception;

/**
 * The type Plan cannot be excluded exception.
 */
public class PlanCannotBeExcludedException extends CannotBeExcludedException {

  /**
   * Instantiates a new Plan cannot be excluded exception.
   */
  public PlanCannotBeExcludedException() {

    super("This Plan cannot be excluded because it has an association!");
  }

}