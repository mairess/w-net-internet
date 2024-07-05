package com.maires.wnet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


/**
 * The type Address.
 */
@Entity(name = "addresses")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String city;

  private String state;

  private String zipCode;

  private String street;

  private Integer streetNumber;

  private String neighborhood;

  private String complement;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @JsonIgnore
  private Customer customer;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "installation_id")
  private Installation installation;


  /**
   * Instantiates a new Address.
   */
  public Address() {
  }


  /**
   * Instantiates a new Address.
   *
   * @param city         the city
   * @param state        the state
   * @param zipCode      the zip code
   * @param street       the street
   * @param streetNumber the street number
   * @param neighborhood the neighborhood
   * @param complement   the complement
   */
  public Address(
      String city,
      String state,
      String zipCode,
      String street,
      Integer streetNumber,
      String neighborhood,
      String complement
  ) {
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.street = street;
    this.streetNumber = streetNumber;
    this.neighborhood = neighborhood;
    this.complement = complement;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets city.
   *
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * Sets city.
   *
   * @param city the city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets state.
   *
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * Sets state.
   *
   * @param state the state
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Gets zip code.
   *
   * @return the zip code
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * Sets zip code.
   *
   * @param zipCode the zip code
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
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
   * Gets customer.
   *
   * @return the customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * Sets customer.
   *
   * @param customer the customer
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   * Gets installation.
   *
   * @return the installation
   */
  public Installation getInstallation() {
    return installation;
  }

  /**
   * Sets installation.
   *
   * @param installation the installation
   */
  public void setInstallation(Installation installation) {
    this.installation = installation;
  }
}