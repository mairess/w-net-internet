package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Technician;

/**
 * The type Technician creation dto.
 */
public record TechnicianCreationDto(String name, String phone, String email) {

  /**
   * To entity technician.
   *
   * @return the technician
   */
  public Technician toEntity() {
    return new Technician(name, phone, email);
  }

}