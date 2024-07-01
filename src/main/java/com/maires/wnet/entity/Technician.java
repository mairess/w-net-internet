package com.maires.wnet.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 * The type Technician.
 */
@Entity
@Table(name = "technicians")
public class Technician {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String phone;

  private String email;

  @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL)
  private List<Installation> installations;

  /**
   * Instantiates a new Technician.
   */
  public Technician() {
  }

  /**
   * Instantiates a new Technician.
   *
   * @param name  the name
   * @param phone the phone
   * @param email the email
   */
  public Technician(String name, String phone, String email) {
    this.name = name;
    this.phone = phone;
    this.email = email;
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
   * Gets phone.
   *
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * Sets phone.
   *
   * @param phone the phone
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Gets email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets email.
   *
   * @param email the email
   */
  public void setEmail(String email) {
    this.email = email;
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