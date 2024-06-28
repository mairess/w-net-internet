package com.maires.wnet.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * The type Installation.
 */
@Entity
@Table(name = "installations")
public class Installation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @OneToOne(mappedBy = "installationFirst", cascade = CascadeType.ALL)
  private Equipment equipmentFirst;

  @OneToOne(mappedBy = "installationSecond", cascade = CascadeType.ALL)
  private Equipment equipmentSecond;

  @OneToOne
  @JoinColumn(name = "plan_id")
  private Plan plan;

  @OneToOne(mappedBy = "installation", cascade = CascadeType.ALL)
  private Technician technician;

  private Date installationDate;

  /**
   * Instantiates a new Installation.
   */
  public Installation() {
  }

  /**
   * Instantiates a new Installation.
   *
   * @param address    the address id
   * @param plan       the plan id
   * @param technician the technician id
   */
  public Installation(Address address, Plan plan, Technician technician) {
    this.address = address;
    this.plan = plan;
    this.technician = technician;
    this.installationDate = new Date();
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
   * Gets address id.
   *
   * @return the address id
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Sets address id.
   *
   * @param address the address id
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * Gets equipment first.
   *
   * @return the equipment first
   */
  public Equipment getEquipmentFirst() {
    return equipmentFirst;
  }

  /**
   * Sets equipment first.
   *
   * @param equipmentFirst the equipment first
   */
  public void setEquipmentFirst(Equipment equipmentFirst) {
    this.equipmentFirst = equipmentFirst;
  }

  /**
   * Gets equipment second.
   *
   * @return the equipment second
   */
  public Equipment getEquipmentSecond() {
    return equipmentSecond;
  }

  /**
   * Sets equipment second.
   *
   * @param equipmentSecond the equipment second
   */
  public void setEquipmentSecond(Equipment equipmentSecond) {
    this.equipmentSecond = equipmentSecond;
  }

  /**
   * Gets plan.
   *
   * @return the plan
   */
  public Plan getPlan() {
    return plan;
  }

  /**
   * Sets plan.
   *
   * @param plan the plan
   */
  public void setPlan(Plan plan) {
    this.plan = plan;
  }

  /**
   * Gets plan id.
   *
   * @return the plan id
   */
  public Plan getPlanId() {
    return plan;
  }

  /**
   * Sets plan id.
   *
   * @param plan the plan id
   */
  public void setPlanId(Plan plan) {
    this.plan = plan;
  }

  /**
   * Gets technician id.
   *
   * @return the technician id
   */
  public Technician getTechnician() {
    return technician;
  }

  /**
   * Sets technician id.
   *
   * @param technician the technician id
   */
  public void setTechnician(Technician technician) {
    this.technician = technician;
  }

  /**
   * Gets installation date.
   *
   * @return the installation date
   */
  public Date getInstallationDate() {
    return installationDate;
  }

  /**
   * Sets installation date.
   *
   * @param installationDate the installation date
   */
  public void setInstallationDate(Date installationDate) {
    this.installationDate = installationDate;
  }
}