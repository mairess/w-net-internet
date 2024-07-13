package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.PlanCreationDto;
import com.maires.wnet.controller.dto.PlanDto;
import com.maires.wnet.entity.Plan;
import com.maires.wnet.service.PlanService;
import com.maires.wnet.service.exception.PlanCannotBeExcludedException;
import com.maires.wnet.service.exception.PlanNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Plan controller.
 */
@RestController
@RequestMapping("/plans")
public class PlanController {

  private final PlanService planService;

  /**
   * Instantiates a new Plan controller.
   *
   * @param planService the plan service
   */
  @Autowired
  public PlanController(PlanService planService) {
    this.planService = planService;
  }

  /**
   * Find all plans list.
   *
   * @return the list
   */
  @GetMapping
  public List<PlanDto> findAllPlans() {
    return planService.findAllPlans().stream().map(PlanDto::fromEntity).toList();
  }

  /**
   * Find plan by id plan dto.
   *
   * @param planId the plan id
   * @return the plan dto
   * @throws PlanNotFoundException the plan not found exception
   */
  @GetMapping("/{planId}")
  public PlanDto findPlanById(@PathVariable Long planId)
      throws PlanNotFoundException {
    return PlanDto.fromEntity(planService.findPlanById(planId));
  }

  /**
   * Create plan plan dto.
   *
   * @param planCreationDto the plan creation dto
   * @return the plan dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('ADMIN')")
  public PlanDto createPlan(@Valid @RequestBody PlanCreationDto planCreationDto) {
    Plan newPlan = planService.createPlan(planCreationDto.toEntity());
    return PlanDto.fromEntity(newPlan);
  }

  /**
   * Remove plan by id plan dto.
   *
   * @param planId the plan id
   * @return the plan dto
   * @throws PlanNotFoundException         the plan not found exception
   * @throws PlanCannotBeExcludedException the plan cannot be excluded exception
   */
  @DeleteMapping("/{planId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public PlanDto removePlanById(@PathVariable Long planId)
      throws PlanNotFoundException, PlanCannotBeExcludedException {
    return PlanDto.fromEntity(planService.removePlanById(planId));
  }

  /**
   * Update plan dto.
   *
   * @param planId          the plan id
   * @param planCreationDto the plan creation dto
   * @return the plan dto
   * @throws PlanNotFoundException the plan not found exception
   */
  @PutMapping("/{planId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public PlanDto updatePlan(
      @Valid
      @PathVariable Long planId,
      @RequestBody PlanCreationDto planCreationDto
  ) throws PlanNotFoundException {
    Plan planToUpdate = planService.updatePlan(planId, planCreationDto.toEntity());
    return PlanDto.fromEntity(planToUpdate);
  }
}