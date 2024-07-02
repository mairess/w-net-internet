package com.maires.wnet.entity;

import jakarta.persistence.CascadeType;
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

  private String name;

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
   * @param name  the name
   * @param speed the speed
   * @param price the price
   */
  public Plan(String name, Integer speed, Double price) {
    this.name = name;
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