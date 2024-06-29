package com.maires.wnet.service;

import com.maires.wnet.entity.Plan;
import com.maires.wnet.repository.PlanRepository;
import com.maires.wnet.service.exception.PlanNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Plan service.
 */
@Service
public class PlanService {

  private final PlanRepository planRepository;

  /**
   * Instantiates a new Plan service.
   *
   * @param planRepository the plan repository
   */
  @Autowired
  public PlanService(PlanRepository planRepository) {
    this.planRepository = planRepository;
  }

  /**
   * Find all plans list.
   *
   * @return the list
   */
  public List<Plan> findAllPlans() {
    return planRepository.findAll();
  }

  /**
   * Find plan by id plan.
   *
   * @param planId the plan id
   * @return the plan
   * @throws PlanNotFoundException the plan not found exception
   */
  public Plan findPlanById(Long planId) throws PlanNotFoundException {
    return planRepository.findById(planId)
        .orElseThrow(PlanNotFoundException::new);
  }

  /**
   * Create plan.
   *
   * @param plan the plan
   * @return the plan
   */
  public Plan createPlan(Plan plan) {
    return planRepository.save(plan);
  }

  /**
   * Remove plan by id plan.
   *
   * @param planId the plan id
   * @return the plan
   * @throws PlanNotFoundException the plan not found exception
   */
  public Plan removePlanById(Long planId) throws PlanNotFoundException {
    Plan deletedPlan = findPlanById(planId);
    planRepository.delete(deletedPlan);
    return deletedPlan;
  }
}