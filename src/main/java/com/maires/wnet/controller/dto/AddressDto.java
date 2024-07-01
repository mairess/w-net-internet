package com.maires.wnet.controller.dto;

/**
 * The interface Address dto.
 */
public interface AddressDto {

  /**
   * Gets id.
   *
   * @return the id
   */
  Long getId();

  /**
   * Gets city.
   *
   * @return the city
   */
  String getCity();

  /**
   * Gets state.
   *
   * @return the state
   */
  String getState();

  /**
   * Gets zip code.
   *
   * @return the zip code
   */
  String getZipCode();
}