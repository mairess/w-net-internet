package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.InstallationCreationDto;
import com.maires.wnet.controller.dto.InstallationDto;
import com.maires.wnet.service.InstallationService;
import com.maires.wnet.service.exception.AddressNotFoundException;
import com.maires.wnet.service.exception.PlanNotFoundException;
import com.maires.wnet.service.exception.TechnicianNotFoundException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
  public List<InstallationDto> findAllInstallations() {
    return installationService.findAllInstallations().stream().map(InstallationDto::fromEntity)
        .toList();
  }


  /**
   * Create installation installation dto.
   *
   * @param installationCreationDto the installation creation dto
   * @return the installation dto
   * @throws AddressNotFoundException    the address not found exception
   * @throws PlanNotFoundException       the plan not found exception
   * @throws TechnicianNotFoundException the technician not found exception
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public InstallationDto createInstallation(
      @RequestBody InstallationCreationDto installationCreationDto)
      throws AddressNotFoundException, PlanNotFoundException, TechnicianNotFoundException {

    return InstallationDto.fromEntity(
        installationService.createInstallation(
            installationCreationDto.addressId(),
            installationCreationDto.planId(),
            installationCreationDto.technicianId(),
            installationCreationDto.equipmentIds()
        )
    );
  }
}