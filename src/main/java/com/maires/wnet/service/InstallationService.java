package com.maires.wnet.service;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Equipment;
import com.maires.wnet.entity.Installation;
import com.maires.wnet.entity.Plan;
import com.maires.wnet.entity.Technician;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.repository.InstallationRepository;
import com.maires.wnet.repository.PlanRepository;
import com.maires.wnet.repository.TechnicianRepository;
import com.maires.wnet.service.exception.AddressAlreadyAssociatedException;
import com.maires.wnet.service.exception.AddressNotFoundException;
import com.maires.wnet.service.exception.EquipmentAlreadyAssociatedException;
import com.maires.wnet.service.exception.EquipmentNotFoundException;
import com.maires.wnet.service.exception.InstallationNotFoundException;
import com.maires.wnet.service.exception.PlanNotFoundException;
import com.maires.wnet.service.exception.TechnicianNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * The type Installation service.
 */
@Service
public class InstallationService {

  private final InstallationRepository installationRepository;
  private final AddressRepository addressRepository;
  private final PlanRepository planRepository;
  private final TechnicianRepository technicianRepository;
  private final EquipmentRepository equipmentRepository;

  /**
   * Instantiates a new Installation service.
   *
   * @param installationRepository the installation repository
   * @param addressRepository      the address repository
   * @param planRepository         the plan repository
   * @param technicianRepository   the technician repository
   * @param equipmentRepository    the equipment repository
   */
  public InstallationService(InstallationRepository installationRepository,
      AddressRepository addressRepository, PlanRepository planRepository,
      TechnicianRepository technicianRepository, EquipmentRepository equipmentRepository) {
    this.installationRepository = installationRepository;
    this.addressRepository = addressRepository;
    this.planRepository = planRepository;
    this.technicianRepository = technicianRepository;
    this.equipmentRepository = equipmentRepository;
  }

  /**
   * Find all installations list.
   *
   * @return the list
   */
  public List<Installation> findAllInstallations() {
    return installationRepository.findAll();
  }

  /**
   * Find installation by id installation.
   *
   * @param installationId the installation id
   * @return the installation
   * @throws InstallationNotFoundException the installation not found exception
   */
  public Installation findInstallationById(Long installationId)
      throws InstallationNotFoundException {
    return installationRepository.findById(installationId).orElseThrow(
        InstallationNotFoundException::new);
  }


  /**
   * Create installation installation.
   *
   * @param addressId    the address id
   * @param planId       the plan id
   * @param technicianId the technician id
   * @param equipmentIds the equipment ids
   * @return the installation
   * @throws AddressNotFoundException            the address not found exception
   * @throws AddressAlreadyAssociatedException   the address already associated exception
   * @throws PlanNotFoundException               the plan not found exception
   * @throws TechnicianNotFoundException         the technician not found exception
   * @throws EquipmentNotFoundException          the equipment not found exception
   * @throws EquipmentAlreadyAssociatedException the equipment already associated exception
   */
  @Transactional
  public Installation createInstallation(
      Long addressId,
      Long planId,
      Long technicianId,
      List<Long> equipmentIds
  )
      throws
      AddressNotFoundException,
      AddressAlreadyAssociatedException,
      PlanNotFoundException,
      TechnicianNotFoundException,
      EquipmentNotFoundException,
      EquipmentAlreadyAssociatedException {

    Address address = addressRepository.findById(addressId)
        .orElseThrow(AddressNotFoundException::new);

    if (address.getInstallation() != null) {
      throw new AddressAlreadyAssociatedException();
    }

    Plan plan = planRepository.findById(planId)
        .orElseThrow(PlanNotFoundException::new);

    Technician technician = technicianRepository.findById(technicianId)
        .orElseThrow(TechnicianNotFoundException::new);

    List<Equipment> equipmentList = new ArrayList<>();

    for (Long id : equipmentIds) {
      Optional<Equipment> equipmentOptional = equipmentRepository.findById(id);
      if (equipmentOptional.isEmpty()) {
        throw new EquipmentNotFoundException();
      }
      Equipment equipment = equipmentOptional.get();

      if (equipment.getInstallation() != null) {
        throw new EquipmentAlreadyAssociatedException();
      }
      equipmentList.add(equipment);
    }

    Installation newInstallation = new Installation(address, plan, technician, equipmentList);

    for (Equipment equipment : equipmentList) {
      equipment.setInstallation(newInstallation);
    }

    return installationRepository.save(newInstallation);
  }

  /**
   * Remove installation by id installation.
   *
   * @param installationId the installation id
   * @return the installation
   * @throws InstallationNotFoundException the installation not found exception
   */
  public Installation removeInstallationById(Long installationId)
      throws InstallationNotFoundException {

    Installation deletedInstallation = findInstallationById(installationId);
    Address address = deletedInstallation.getAddress();

    if (address != null) {
      address.setInstallation(null);
    }

    List<Equipment> equipmentList = deletedInstallation.getEquipments();

    for (Equipment equipment : equipmentList) {
      equipment.setInstallation(null);
    }

    installationRepository.delete(deletedInstallation);
    return deletedInstallation;

  }

}