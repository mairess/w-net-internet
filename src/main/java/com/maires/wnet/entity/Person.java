package com.maires.wnet.entity;

import com.maires.wnet.security.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The type Person.
 */
@Entity
@Table(name = "persons")
public class Person implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fullName;
  @Column(unique = true)
  private String email;
  @Column(unique = true)
  private String username;
  private String password;
  private Role role;

  /**
   * Instantiates a new Person.
   */
  public Person() {
  }

  /**
   * Instantiates a new Person.
   *
   * @param username the username
   * @param password the password
   * @param role     the role
   */
  public Person(Long id, String fullName, String email, String username, String password,
      Role role
  ) {
    this.id = id;
    this.fullName = fullName;
    this.email = email;
    this.username = username;
    this.password = password;
    this.role = role;
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

  public String getUsername() {
    return username;
  }

  /**
   * Sets username.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  public String getPassword() {
    return password;
  }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets role.
   *
   * @return the role
   */
  public Role getRole() {
    return role;
  }

  /**
   * Sets role.
   *
   * @param role the role
   */
  public void setRole(Role role) {
    this.role = role;
  }


}