package com.maires.wnet.service.exception;

/**
 * The type Plan already associated exception.
 */
public class PlanAlreadyAssociatedException extends AlreadyAssociatedException {

  /**
   * Instantiates a new Plan already associated exception.
   */
  public PlanAlreadyAssociatedException() {
    super("This plan is already associated!");
  }
}