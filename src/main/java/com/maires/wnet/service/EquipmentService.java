package com.maires.wnet.service;

import com.maires.wnet.entity.Equipment;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.repository.InstallationRepository;
import com.maires.wnet.service.exception.EquipmentCannotBeExcludedException;
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
   * @param equipmentRepository    the equipment repository
   * @param installationRepository the installation repository
   */
  @Autowired
  public EquipmentService(EquipmentRepository equipmentRepository,
      InstallationRepository installationRepository) {
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
    return equipmentRepository.findById(equipmentId)
        .orElseThrow(() -> new EquipmentNotFoundException(equipmentId.toString()));
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
   * @throws EquipmentNotFoundException         the equipment not found exception
   * @throws EquipmentCannotBeExcludedException the equipment cannot be excluded exception
   */
  public Equipment removeEquipmentById(Long equipmentId)
      throws EquipmentNotFoundException, EquipmentCannotBeExcludedException {

    Equipment deletedEquipment = findEquipmentById(equipmentId);

    if (deletedEquipment.getInstallation() != null) {
      throw new EquipmentCannotBeExcludedException();
    }

    equipmentRepository.delete(deletedEquipment);
    return deletedEquipment;
  }

  /**
   * Update equipment.
   *
   * @param equipmentId the equipment id
   * @param equipment   the equipment
   * @return the equipment
   * @throws EquipmentNotFoundException the equipment not found exception
   */
  public Equipment updateEquipment(Long equipmentId, Equipment equipment)
      throws EquipmentNotFoundException {
    Equipment equipmentToUpdate = findEquipmentById(equipmentId);

    equipmentToUpdate.setType(equipment.getType());
    equipmentToUpdate.setModel(equipment.getModel());
    equipmentToUpdate.setSerialNumber(equipment.getSerialNumber());
    equipmentToUpdate.setManufacturer(equipment.getManufacturer());

    return equipmentRepository.save(equipmentToUpdate);

  }
}