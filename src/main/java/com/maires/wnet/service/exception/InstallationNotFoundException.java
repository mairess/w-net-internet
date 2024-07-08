package com.maires.wnet.service.exception;


/**
 * The type Installation not found exception.
 */
public class InstallationNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Installation not found exception.
   */
  public InstallationNotFoundException(String identifier) {
    super("Installation not found with identifier " + identifier + "!");
  }

}