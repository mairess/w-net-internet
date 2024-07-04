package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.InstallationDto;
import com.maires.wnet.service.InstallationService;
import com.maires.wnet.service.exception.InstallationNotFoundException;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public List<InstallationDto> findAllInstallations() {
    return installationService.findAllInstallations().stream()
        .map(InstallationDto::fromEntity)
        .toList();
  }

  /**
   * Find installation by id installation dto.
   *
   * @param installationId the installation id
   * @return the installation dto
   * @throws InstallationNotFoundException the installation not found exception
   */
  @GetMapping("/{installationId}")
  public InstallationDto findInstallationById(@PathVariable Long installationId)
      throws InstallationNotFoundException {
    return InstallationDto.fromEntity(
        installationService.findInstallationById(installationId)
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
  public InstallationDto removeInstallationById(@PathVariable Long installationId)
      throws InstallationNotFoundException {
    InstallationController planService;
    return InstallationDto.fromEntity(
        installationService.removeInstallationById(installationId)
    );
  }


}