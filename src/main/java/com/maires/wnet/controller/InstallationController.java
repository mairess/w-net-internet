package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.InstallationCreationDto;
import com.maires.wnet.controller.dto.InstallationDto;
import com.maires.wnet.service.InstallationService;
import com.maires.wnet.service.exception.EquipmentAlreadyAssociatedException;
import com.maires.wnet.service.exception.EquipmentNotFoundException;
import com.maires.wnet.service.exception.InstallationNotFoundException;
import com.maires.wnet.service.exception.PlanNotFoundException;
import com.maires.wnet.service.exception.TechnicianNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Installation controller.
 */
@RestController
@RequestMapping("/installations")
public class InstallationController {

  private final InstallationService installationService;


  /**
   * Instantiates a new Installation controller.
   *
   * @param installationService the installation service
   */
  public InstallationController(InstallationService installationService) {
    this.installationService = installationService;
  }


  /**
   * Find all installations list.
   *
   * @return the list
   */
  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public List<InstallationDto> findAllInstallations() {
    return installationService.findAllInstallations().stream()
        .map(InstallationDto::fromEntity).toList();
  }

  /**
   * Find installation by id installation dto.
   *
   * @param installationId the installation id
   * @return the installation dto
   * @throws InstallationNotFoundException the installation not found exception
   */
  @GetMapping("/{installationId}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public InstallationDto findInstallationById(@PathVariable Long installationId)
      throws InstallationNotFoundException {
    return InstallationDto.fromEntity(installationService.findInstallationById(installationId));
  }

  /**
   * Update installation installation dto.
   *
   * @param installationId          the installation id
   * @param installationCreationDto the installation creation dto
   * @return the installation dto
   * @throws InstallationNotFoundException       the installation not found exception
   * @throws EquipmentAlreadyAssociatedException the equipment already associated exception
   * @throws EquipmentNotFoundException          the equipment not found exception
   * @throws TechnicianNotFoundException         the technician not found exception
   * @throws PlanNotFoundException               the plan not found exception
   */
  @PutMapping("/{installationId}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public InstallationDto updateInstallation(
      @Valid
      @PathVariable Long installationId,
      @RequestBody InstallationCreationDto installationCreationDto
  ) throws
      InstallationNotFoundException,
      EquipmentAlreadyAssociatedException,
      EquipmentNotFoundException,
      TechnicianNotFoundException,
      PlanNotFoundException {

    return InstallationDto.fromEntity(
        installationService.updateInstallation(
            installationId,
            installationCreationDto.planId(),
            installationCreationDto.technicianId(),
            installationCreationDto.equipmentIds()
        )
    );
  }


  /**
   * Remove installation by id installation dto.
   *
   * @param installationId the installation id
   * @return the installation dto
   * @throws InstallationNotFoundException the installation not found exception
   */
  @DeleteMapping("/{installationId}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'TECHNICIAN')")
  public InstallationDto removeInstallationById(@PathVariable Long installationId)
      throws InstallationNotFoundException {
    return InstallationDto.fromEntity(installationService.removeInstallationById(installationId));
  }

}