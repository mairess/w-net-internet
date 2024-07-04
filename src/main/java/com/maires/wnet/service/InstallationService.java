package com.maires.wnet.service;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Installation;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.repository.InstallationRepository;
import com.maires.wnet.service.exception.InstallationNotFoundException;
import jakarta.transaction.Transactional;
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


  /**
   * Instantiates a new Installation service.
   *
   * @param installationRepository the installation repository
   * @param addressRepository      the address repository
   * @param equipmentRepository    the equipment repository
   */
  public InstallationService(
      InstallationRepository installationRepository,
      AddressRepository addressRepository,
      EquipmentRepository equipmentRepository
  ) {
    this.installationRepository = installationRepository;
    this.addressRepository = addressRepository;
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