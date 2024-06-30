package com.maires.wnet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * The type Equipment.
 */
@Entity
@Table(name = "equipments")
public class Equipment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  private String type;

  private String model;

  private String serialNumber;

  private String manufacturer;

  private Date provisionDate;

  @ManyToOne
  @JoinColumn(name = "installation_id")
  private Installation installation;

  /**
   * Instantiates a new Equipment.
   */
  public Equipment() {
  }


  /**
   * Instantiates a new Equipment.
   *
   * @param type          the type
   * @param model         the model
   * @param serialNumber  the serial number
   * @param manufacturer  the manufacturer
   * @param provisionDate the provision date
   */
  public Equipment(String type, String model, String serialNumber, String manufacturer,
      Date provisionDate) {
    this.type = type;
    this.model = model;
    this.serialNumber = serialNumber;
    this.manufacturer = manufacturer;
    this.provisionDate = provisionDate;
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
   * Gets type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets type.
   *
   * @param type the type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Gets model.
   *
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * Sets model.
   *
   * @param model the model
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * Gets serial number.
   *
   * @return the serial number
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Sets serial number.
   *
   * @param serialNumber the serial number
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Gets manufacturer.
   *
   * @return the manufacturer
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Sets manufacturer.
   *
   * @param manufacturer the manufacturer
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }


  /**
   * Gets provision date.
   *
   * @return the provision date
   */
  public Date getProvisionDate() {
    return provisionDate;
  }


  /**
   * Sets provision date.
   *
   * @param provisionDate the provision date
   */
  public void setProvisionDate(Date provisionDate) {
    this.provisionDate = provisionDate;
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