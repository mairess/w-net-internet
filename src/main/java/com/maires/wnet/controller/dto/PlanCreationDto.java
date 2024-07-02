package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Plan;

/**
 * The type Plan creation dto.
 */
public record PlanCreationDto(String name, Integer speed, double price) {

  /**
   * To entity plan.
   *
   * @return the plan
   */
  public Plan toEntity() {
    return new Plan(name, speed, price);
  }
}