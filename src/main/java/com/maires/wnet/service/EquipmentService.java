package com.maires.wnet.service;

import com.maires.wnet.entity.Equipment;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.service.exception.EquipmentNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Equipment service.
 */
@Service
public class EquipmentService {

  private final EquipmentRepository equipmentRepository;

  /**
   * Instantiates a new Equipment service.
   *
   * @param equipmentRepository the equipment repository
   */
  @Autowired
  public EquipmentService(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

  /**
   * Find all equipments list.
   *
   * @return the list
   */
  public List<Equipment> findAllEquipments() {
    return equipmentRepository.findAll();
  }

  /**
   * Find equipment by id equipment.
   *
   * @param equipmentId the equipment id
   * @return the equipment
   * @throws EquipmentNotFoundException the equipment not found exception
   */
  public Equipment findEquipmentById(Long equipmentId) throws EquipmentNotFoundException {
    return equipmentRepository.findById(equipmentId).orElseThrow(EquipmentNotFoundException::new);
  }

  /**
   * Create equipment equipment.
   *
   * @param equipment the equipment
   * @return the equipment
   */
  public Equipment createEquipment(Equipment equipment) {
    return equipmentRepository.save(equipment);
  }

  /**
   * Remove equipment by id equipment.
   *
   * @param equipmentId the equipment id
   * @return the equipment
   * @throws EquipmentNotFoundException the equipment not found exception
   */
  public Equipment removeEquipmentById(Long equipmentId) throws EquipmentNotFoundException {
    Equipment deletedEquipment = findEquipmentById(equipmentId);
    equipmentRepository.delete(deletedEquipment);
    return deletedEquipment;
  }

}