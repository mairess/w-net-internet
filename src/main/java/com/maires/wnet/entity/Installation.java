package com.maires.wnet.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

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

  @OneToMany(mappedBy = "installation", cascade = CascadeType.ALL)
  private List<Equipment> equipments;

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
   * Gets address.
   *
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Sets address.
   *
   * @param address the address
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * Gets equipments.
   *
   * @return the equipments
   */
  public List<Equipment> getEquipments() {
    return equipments;
  }

  /**
   * Sets equipments.
   *
   * @param equipments the equipments
   */
  public void setEquipments(List<Equipment> equipments) {
    this.equipments = equipments;
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
   * Gets technician.
   *
   * @return the technician
   */
  public Technician getTechnician() {
    return technician;
  }

  /**
   * Sets technician.
   *
   * @param technician the technician
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