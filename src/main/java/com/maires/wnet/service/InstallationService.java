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
import com.maires.wnet.service.exception.EquipmentAlreadyAssociatedException;
import com.maires.wnet.service.exception.EquipmentNotFoundException;
import com.maires.wnet.service.exception.InstallationNotFoundException;
import com.maires.wnet.service.exception.PlanNotFoundException;
import com.maires.wnet.service.exception.TechnicianNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;


/**
 * The type Installation service.
 */
@Service
public class InstallationService {

  private final InstallationRepository installationRepository;
  private final AddressRepository addressRepository;
  private final EquipmentRepository equipmentRepository;
  private final PlanRepository planRepository;
  private final TechnicianRepository technicianRepository;


  /**
   * Instantiates a new Installation service.
   *
   * @param installationRepository the installation repository
   * @param addressRepository      the address repository
   * @param equipmentRepository    the equipment repository
   * @param planRepository         the plan repository
   * @param technicianRepository   the technician repository
   */
  public InstallationService(
      InstallationRepository installationRepository,
      AddressRepository addressRepository,
      EquipmentRepository equipmentRepository,
      PlanRepository planRepository,
      TechnicianRepository technicianRepository
  ) {
    this.installationRepository = installationRepository;
    this.addressRepository = addressRepository;
    this.equipmentRepository = equipmentRepository;
    this.planRepository = planRepository;
    this.technicianRepository = technicianRepository;
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
        () -> new InstallationNotFoundException(installationId.toString()));
  }

  /**
   * Update installation installation.
   *
   * @param installationId the installation id
   * @param planId         the plan id
   * @param technicianId   the technician id
   * @param equipmentIds   the equipment ids
   * @return the installation
   * @throws InstallationNotFoundException       the installation not found exception
   * @throws PlanNotFoundException               the plan not found exception
   * @throws TechnicianNotFoundException         the technician not found exception
   * @throws EquipmentNotFoundException          the equipment not found exception
   * @throws EquipmentAlreadyAssociatedException the equipment already associated exception
   */
  @Transactional
  public Installation updateInstallation(
      Long installationId,
      Long planId,
      Long technicianId,
      List<Long> equipmentIds
  ) throws
      InstallationNotFoundException,
      PlanNotFoundException,
      TechnicianNotFoundException,
      EquipmentNotFoundException,
      EquipmentAlreadyAssociatedException {

    Installation installationToUpdate = findInstallationById(installationId);

    installationToUpdate.getEquipments()
        .forEach(equipment -> {
          equipment.setInstallation(null);
          equipmentRepository.save(equipment);
        });

    installationToUpdate.getEquipments().clear();

    if (!equipmentIds.isEmpty()) {

      List<Equipment> equipmentList = new ArrayList<>();

      for (Long id : equipmentIds) {

        Equipment equipment = equipmentRepository.findById(id)
            .orElseThrow(() -> new EquipmentNotFoundException(id.toString()));

        if (equipment.getInstallation() != null) {
          throw new EquipmentAlreadyAssociatedException();
        }
        equipmentList.add(equipment);
        equipment.setInstallation(installationToUpdate);
        equipmentRepository.save(equipment);
      }

      installationToUpdate.setEquipments(equipmentList);
    }

    Plan plan = planRepository.findById(planId)
        .orElseThrow(() -> new PlanNotFoundException(planId.toString()));

    Technician technician = technicianRepository.findById(technicianId)
        .orElseThrow(() -> new TechnicianNotFoundException(technicianId.toString()));

    installationToUpdate.setPlan(plan);
    installationToUpdate.setTechnician(technician);
    return installationRepository.save(installationToUpdate);
  }


  /**
   * Remove installation by id installation.
   *
   * @param installationId the installation id
   * @return the installation
   * @throws InstallationNotFoundException the installation not found exception
   */
  @Transactional
  public Installation removeInstallationById(Long installationId)
      throws InstallationNotFoundException {

    Installation deletedInstallation = findInstallationById(installationId);
    Address address = deletedInstallation.getAddress();

    if (address != null) {
      address.setInstallation(null);
      addressRepository.save(address);
    }

    deletedInstallation.getEquipments()
        .forEach(equipment -> {
          equipment.setInstallation(null);
          equipmentRepository.save(equipment);
        });

    installationRepository.delete(deletedInstallation);
    return deletedInstallation;

  }

}