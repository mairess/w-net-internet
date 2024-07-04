package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.TechnicianCreationDto;
import com.maires.wnet.controller.dto.TechnicianDto;
import com.maires.wnet.entity.Technician;
import com.maires.wnet.service.TechnicianService;
import com.maires.wnet.service.exception.TechnicianCannotBeExcludedException;
import com.maires.wnet.service.exception.TechnicianNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * The type Technician controller.
 */
@RestController
@RequestMapping("/technicians")
public class TechnicianController {

  private final TechnicianService technicianService;

  /**
   * Instantiates a new Technician controller.
   *
   * @param technicianService the technician service
   */
  @Autowired
  public TechnicianController(TechnicianService technicianService) {
    this.technicianService = technicianService;
  }

  /**
   * Find all technicians list.
   *
   * @return the list
   */
  @GetMapping
  public List<TechnicianDto> findAllTechnicians() {
    return technicianService.findAllTechnicians().stream().map(TechnicianDto::fromEntity).toList();
  }

  /**
   * Find technician by id technician dto.
   *
   * @param technicianId the technician id
   * @return the technician dto
   * @throws TechnicianNotFoundException the technician not found exception
   */
  @GetMapping("/{technicianId}")
  public TechnicianDto findTechnicianById(@PathVariable Long technicianId)
      throws TechnicianNotFoundException {
    return TechnicianDto.fromEntity(technicianService.findTechnicianById(technicianId));
  }

  /**
   * Create technician dto.
   *
   * @param technicianCreationDto the technician creation dto
   * @return the technician dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TechnicianDto createTechnician(@RequestBody TechnicianCreationDto technicianCreationDto) {
    Technician newTechnician = technicianService.createTechnician(technicianCreationDto.toEntity());
    return TechnicianDto.fromEntity(newTechnician);
  }

  /**
   * Remove technician by id technician dto.
   *
   * @param technicianId the technician id
   * @return the technician dto
   * @throws TechnicianNotFoundException         the technician not found exception
   * @throws TechnicianCannotBeExcludedException the technician cannot be excluded exception
   */
  @DeleteMapping("/{technicianId}")
  public TechnicianDto removeTechnicianById(@PathVariable Long technicianId)
      throws TechnicianNotFoundException, TechnicianCannotBeExcludedException {
    return TechnicianDto.fromEntity(technicianService.removeTechnicianById(technicianId));
  }

  /**
   * Update technician dto.
   *
   * @param technicianId          the technician id
   * @param technicianCreationDto the technician creation dto
   * @return the technician dto
   * @throws TechnicianNotFoundException the technician not found exception
   */
  @PutMapping("/{technicianId}")
  public TechnicianDto updateTechnician(
      @PathVariable Long technicianId,
      @RequestBody TechnicianCreationDto technicianCreationDto
  ) throws TechnicianNotFoundException {
    Technician updatedTechnician = technicianService
        .updateTechnician(technicianId, technicianCreationDto.toEntity());
    return TechnicianDto.fromEntity(updatedTechnician);
  }

}