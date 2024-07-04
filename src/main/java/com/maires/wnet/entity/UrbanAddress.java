package com.maires.wnet.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * The type Urban address.
 */
@Entity
@DiscriminatorValue("urban")
public class UrbanAddress extends Address {

  private String street;

  private Integer streetNumber;

  private String complement;

  private String neighborhood;

  /**
   * Instantiates a new Urban address.
   */
  public UrbanAddress() {
    super();
  }

  /**
   * Instantiates a new Urban address.
   *
   * @param city         the city
   * @param state        the state
   * @param zipCode      the zipCode
   * @param street       the street
   * @param streetNumber the street number
   * @param complement   the complement
   * @param neighborhood the neighborhood
   */
  public UrbanAddress(String city, String state, String zipCode, String street, int streetNumber,
      String complement, String neighborhood) {
    super(city, state, zipCode);
    this.street = street;
    this.streetNumber = streetNumber;
    this.complement = complement;
    this.neighborhood = neighborhood;
  }

  /**
   * Gets street.
   *
   * @return the street
   */
  public String getStreet() {
    return street;
  }

  /**
   * Sets street.
   *
   * @param street the street
   */
  public void setStreet(String street) {
    this.street = street;
  }

  /**
   * Gets street number.
   *
   * @return the street number
   */
  public Integer getStreetNumber() {
    return streetNumber;
  }

  /**
   * Sets street number.
   *
   * @param streetNumber the street number
   */
  public void setStreetNumber(Integer streetNumber) {
    this.streetNumber = streetNumber;
  }

  /**
   * Gets complement.
   *
   * @return the complement
   */
  public String getComplement() {
    return complement;
  }

  /**
   * Sets complement.
   *
   * @param complement the complement
   */
  public void setComplement(String complement) {
    this.complement = complement;
  }

  /**
   * Gets neighborhood.
   *
   * @return the neighborhood
   */
  public String getNeighborhood() {
    return neighborhood;
  }

  /**
   * Sets neighborhood.
   *
   * @param neighborhood the neighborhood
   */
  public void setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
  }
}