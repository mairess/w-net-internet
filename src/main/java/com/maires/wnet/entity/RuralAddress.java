package com.maires.wnet.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * The type Rural address.
 */
@Entity
@DiscriminatorValue("rural")
public class RuralAddress extends Address {

  private String farmName;

  private String village;

  /**
   * Instantiates a new Rural address.
   */
  public RuralAddress() {
    super();
  }

  /**
   * Instantiates a new Rural address.
   *
   * @param city     the city
   * @param state    the state
   * @param zipCode  the zip code
   * @param farmName the farm name
   * @param village  the village
   */
  public RuralAddress(String city, String state, String zipCode, String farmName, String village) {
    super(city, state, zipCode);
    this.farmName = farmName;
    this.village = village;
  }

  /**
   * Gets farm name.
   *
   * @return the farm name
   */
  public String getFarmName() {
    return farmName;
  }

  /**
   * Sets farm name.
   *
   * @param farmName the farm name
   */
  public void setFarmName(String farmName) {
    this.farmName = farmName;
  }

  /**
   * Gets village.
   *
   * @return the village
   */
  public String getVillage() {
    return village;
  }

  /**
   * Sets village.
   *
   * @param village the village
   */
  public void setVillage(String village) {
    this.village = village;
  }
}