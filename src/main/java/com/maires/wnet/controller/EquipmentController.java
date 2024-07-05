package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.EquipmentCreationDto;
import com.maires.wnet.controller.dto.EquipmentDto;
import com.maires.wnet.entity.Equipment;
import com.maires.wnet.service.EquipmentService;
import com.maires.wnet.service.exception.EquipmentCannotBeExcludedException;
import com.maires.wnet.service.exception.EquipmentNotFoundException;
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
 * The type Equipment controller.
 */
@RestController
@RequestMapping("/equipments")
public class EquipmentController {

  private final EquipmentService equipmentService;

  /**
   * Instantiates a new Equipment controller.
   *
   * @param equipmentService the equipment service
   */
  @Autowired
  public EquipmentController(EquipmentService equipmentService) {
    this.equipmentService = equipmentService;
  }

  /**
   * Find all equipments list.
   *
   * @return the list
   */
  @GetMapping
  public List<EquipmentDto> findAllEquipments() {
    return equipmentService.findAllEquipments().stream().map(EquipmentDto::fromEntity).toList();
  }

  /**
   * Find equipment by id equipment dto.
   *
   * @param equipmentId the equipment id
   * @return the equipment dto
   * @throws EquipmentNotFoundException the equipment not found exception
   */
  @GetMapping("/{equipmentId}")
  public EquipmentDto findEquipmentById(@PathVariable Long equipmentId)
      throws EquipmentNotFoundException {
    return EquipmentDto.fromEntity(equipmentService.findEquipmentById(equipmentId));
  }

  /**
   * Create equipment equipment dto.
   *
   * @param equipmentCreationDto the equipment creation dto
   * @return the equipment dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EquipmentDto createEquipment(@RequestBody EquipmentCreationDto equipmentCreationDto) {
    Equipment newEquipment = equipmentService.createEquipment(equipmentCreationDto.toEntity());
    return EquipmentDto.fromEntity(newEquipment);
  }

  /**
   * Remove equipment by id equipment dto.
   *
   * @param equipmentId the equipment id
   * @return the equipment dto
   * @throws EquipmentNotFoundException         the equipment not found exception
   * @throws EquipmentCannotBeExcludedException the equipment cannot be excluded exception
   */
  @DeleteMapping("/{equipmentId}")
  public EquipmentDto removeEquipmentById(@PathVariable Long equipmentId)
      throws EquipmentNotFoundException, EquipmentCannotBeExcludedException {
    return EquipmentDto.fromEntity(equipmentService.removeEquipmentById(equipmentId));
  }

  /**
   * Update equipment dto.
   *
   * @param equipmentId          the equipment id
   * @param equipmentCreationDto the equipment creation dto
   * @return the equipment dto
   * @throws EquipmentNotFoundException the equipment not found exception
   */
  @PutMapping("/{equipmentId}")
  public EquipmentDto updateEquipment(
      @PathVariable Long equipmentId,
      @RequestBody EquipmentCreationDto equipmentCreationDto
  ) throws EquipmentNotFoundException {
    Equipment equipmentToUpdate = equipmentService.updateEquipment(equipmentId,
        equipmentCreationDto.toEntity());
    return EquipmentDto.fromEntity(equipmentToUpdate);
  }

}