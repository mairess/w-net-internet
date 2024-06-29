package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Plan;

/**
 * The type Plan dto.
 */
public record PlanDto(Long id, String name, Integer speed, double price, boolean isActive) {

  /**
   * From entity plan dto.
   *
   * @param plan the plan
   * @return the plan dto
   */
  public static PlanDto fromEntity(Plan plan) {
    return new PlanDto(
        plan.getId(),
        plan.getName(),
        plan.getSpeed(),
        plan.getPrice(),
        plan.isActive()
    );
  }

}