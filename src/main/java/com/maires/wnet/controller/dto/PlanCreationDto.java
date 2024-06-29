package com.maires.wnet.controller.dto;

import com.maires.wnet.entity.Plan;

public record PlanCreationDto(String name, Integer speed, double price, boolean isActive) {

  public Plan toEntity() {
    return new Plan(name, speed, price, isActive);
  }
}