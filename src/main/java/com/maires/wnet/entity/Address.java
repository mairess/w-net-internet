package com.maires.wnet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * The type Address.
 */
@Entity(name = "addresses")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "address_type",
    discriminatorType = DiscriminatorType.STRING)
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String city;

  private String state;

  private String zipCode;

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
   * @param city    the city
   * @param state   the state
   * @param zipCode the zipCode
   */
  public Address(String city, String state, String zipCode) {
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
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
   * Gets zipCode.
   *
   * @return the zipCode
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * Sets zipCode.
   *
   * @param zipCode the zipCode
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
}