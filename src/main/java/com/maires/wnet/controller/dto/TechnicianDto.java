package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Technician;

/**
 * The type Technician dto.
 */
public record TechnicianDto(Long id, String name, String phone, String email) {

  /**
   * From entity technician dto.
   *
   * @param technician the technician
   * @return the technician dto
   */
  public static TechnicianDto fromEntity(Technician technician) {
    return new TechnicianDto(
        technician.getId(),
        technician.getName(),
        technician.getPhone(),
        technician.getEmail()
    );
  }

}