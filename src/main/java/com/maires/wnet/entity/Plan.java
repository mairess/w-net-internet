package com.maires.wnet.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Plan.
 */
@Entity
@Table(name = "plans")
public class Plan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String fullName;

  private Integer speed;

  private Double price;

  @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
  private List<Installation> installations = new ArrayList<>();

  /**
   * Instantiates a new Plan.
   */
  public Plan() {
  }


  /**
   * Instantiates a new Plan.
   *
   * @param fullName the full name
   * @param speed    the speed
   * @param price    the price
   */
  public Plan(String fullName, Integer speed, Double price) {
    this.fullName = fullName;
    this.speed = speed;
    this.price = price;
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
   * Gets full name.
   *
   * @return the full name
   */
  public String getFullName() {
    return fullName;
  }


  /**
   * Sets full name.
   *
   * @param fullName the full name
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
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
   * Gets installations.
   *
   * @return the installations
   */
  public List<Installation> getInstallations() {
    return installations;
  }

  /**
   * Sets installations.
   *
   * @param installations the installations
   */
  public void setInstallations(List<Installation> installations) {
    this.installations = installations;
  }
}