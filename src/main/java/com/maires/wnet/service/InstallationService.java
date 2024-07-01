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
import com.maires.wnet.service.exception.AddressNotFoundException;
import com.maires.wnet.service.exception.PlanNotFoundException;
import com.maires.wnet.service.exception.TechnicianNotFoundException;
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
   * Create installation installation.
   *
   * @param addressId    the address id
   * @param planId       the plan id
   * @param technicianId the technician id
   * @param equipmentIds the equipment ids
   * @return the installation
   * @throws AddressNotFoundException    the address not found exception
   * @throws PlanNotFoundException       the plan not found exception
   * @throws TechnicianNotFoundException the technician not found exception
   */
  public Installation createInstallation(Long addressId, Long planId, Long technicianId,
      List<Long> equipmentIds)
      throws AddressNotFoundException, PlanNotFoundException, TechnicianNotFoundException {

    Address address = addressRepository.findById(addressId)
        .orElseThrow(AddressNotFoundException::new);

    Plan plan = planRepository.findById(planId)
        .orElseThrow(PlanNotFoundException::new);

    Technician technician = technicianRepository.findById(technicianId)
        .orElseThrow(TechnicianNotFoundException::new);

    List<Equipment> equipmentList = new ArrayList<>();

    for (Long id : equipmentIds) {
      Optional<Equipment> equipmentOptional = equipmentRepository.findById(id);
      equipmentOptional.ifPresent(equipmentList::add);
    }

    Installation newInstallation = new Installation(address, plan, technician, equipmentList);

    for (Equipment equipment : equipmentList) {
      equipment.setInstallation(newInstallation);
    }

    return installationRepository.save(newInstallation);
  }

}