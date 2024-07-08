package com.maires.wnet.service;

import com.maires.wnet.entity.Plan;
import com.maires.wnet.repository.PlanRepository;
import com.maires.wnet.service.exception.PlanCannotBeExcludedException;
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
        .orElseThrow(() -> new PlanNotFoundException(planId.toString()));
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
   * @throws PlanNotFoundException         the plan not found exception
   * @throws PlanCannotBeExcludedException the plan cannot be excluded exception
   */
  public Plan removePlanById(Long planId)
      throws PlanNotFoundException, PlanCannotBeExcludedException {
    Plan deletedPlan = findPlanById(planId);

    if (!deletedPlan.getInstallations().isEmpty()) {
      throw new PlanCannotBeExcludedException();
    }
    planRepository.delete(deletedPlan);
    return deletedPlan;
  }

  /**
   * Update plan plan.
   *
   * @param planId the plan id
   * @param plan   the plan
   * @return the plan
   * @throws PlanNotFoundException the plan not found exception
   */
  public Plan updatePlan(Long planId, Plan plan) throws PlanNotFoundException {
    Plan planToUpdate = findPlanById(planId);

    planToUpdate.setFullName(plan.getFullName());
    planToUpdate.setSpeed(plan.getSpeed());
    planToUpdate.setPrice(plan.getPrice());

    return planRepository.save(planToUpdate);
  }
}