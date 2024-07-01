package com.maires.wnet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The type Equipment.
 */
@Entity
@Table(name = "equipments")
public class Equipment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private EquipmentType type;

  private String model;

  private String serialNumber;

  private String manufacturer;

  /**
   * Instantiates a new Equipment.
   */
  public Equipment() {
  }


  /**
   * Instantiates a new Equipment.
   *
   * @param type         the type
   * @param model        the model
   * @param serialNumber the serial number
   * @param manufacturer the manufacturer
   */
  public Equipment(EquipmentType type, String model, String serialNumber, String manufacturer) {
    this.type = type;
    this.model = model;
    this.serialNumber = serialNumber;
    this.manufacturer = manufacturer;
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
  public EquipmentType getType() {
    return type;
  }

  /**
   * Sets type.
   *
   * @param type the type
   */
  public void setType(EquipmentType type) {
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

}