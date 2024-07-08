package com.maires.wnet.entity;

import com.maires.wnet.utils.DateUtil;
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
 * The type Customer.
 */
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String fullName;

  @Column(unique = true)
  private String cpf;

  private String phone;

  @Column(unique = true)
  private String email;

  private String registrationDate;


  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<Address> addresses = new ArrayList<>();

  /**
   * Instantiates a new Customer.
   */
  public Customer() {
  }

  /**
   * Instantiates a new Customer.
   *
   * @param fullName the fullName
   * @param cpf      the cpf
   * @param phone    the phone
   * @param email    the email
   */
  public Customer(String fullName, String cpf, String phone, String email) {
    this.fullName = fullName;
    this.cpf = cpf;
    this.phone = phone;
    this.email = email;
    this.registrationDate = DateUtil.formatCurrentDate();
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
   * Gets fullName.
   *
   * @return the fullName
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Sets fullName.
   *
   * @param fullName the fullName
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * Gets cpf.
   *
   * @return the cpf
   */
  public String getCpf() {
    return cpf;
  }

  /**
   * Sets cpf.
   *
   * @param cpf the cpf
   */
  public void setCpf(String cpf) {
    this.cpf = cpf;
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
   * Gets registration date.
   *
   * @return the registration date
   */
  public String getRegistrationDate() {
    return registrationDate;
  }

  /**
   * Sets registration date.
   *
   * @param registrationDate the registration date
   */
  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  /**
   * Gets addresses.
   *
   * @return the addresses
   */
  public List<Address> getAddresses() {
    return addresses;
  }

  /**
   * Sets addresses.
   *
   * @param addresses the addresses
   */
  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }
}