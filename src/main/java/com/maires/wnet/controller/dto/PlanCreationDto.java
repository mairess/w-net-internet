package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Plan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * The type Plan creation dto.
 */
public record PlanCreationDto(
    @NotNull(message = "cannot be null!")
    @NotBlank(message = "cannot be blank!")
    @Size(max = 20, message = "must be at least 20 characters!")
    String name,

    @NotNull(message = "cannot be null!")
    Integer speed,

    @NotNull(message = "cannot be null!")
    double price
) {

  /**
   * To entity plan.
   *
   * @return the plan
   */
  public Plan toEntity() {
    return new Plan(name, speed, price);
  }
}