package com.maires.wnet.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * The type Plan.
 */
@Entity
@Table(name = "plans")
public class Plan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Integer speed;

  private Double price;

  private boolean isActive;

  @OneToOne(mappedBy = "plan", cascade = CascadeType.ALL)
  private Installation installation;

  /**
   * Instantiates a new Plan.
   */
  public Plan() {
  }

  /**
   * Instantiates a new Plan.
   *
   * @param name         the name
   * @param speed        the speed
   * @param price        the price
   * @param isActive     the is active
   * @param installation the installation
   */
  public Plan(String name, Integer speed, Double price, boolean isActive,
      Installation installation) {
    this.name = name;
    this.speed = speed;
    this.price = price;
    this.isActive = isActive;
    this.installation = installation;
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
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets speed.
   *
   * @return the speed
   */
  public Integer getSpeed() {
    return speed;
  }

  /**
   * Sets speed.
   *
   * @param speed the speed
   */
  public void setSpeed(Integer speed) {
    this.speed = speed;
  }

  /**
   * Gets price.
   *
   * @return the price
   */
  public Double getPrice() {
    return price;
  }

  /**
   * Sets price.
   *
   * @param price the price
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Is active boolean.
   *
   * @return the boolean
   */
  public boolean isActive() {
    return isActive;
  }

  /**
   * Sets active.
   *
   * @param active the active
   */
  public void setActive(boolean active) {
    isActive = active;
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