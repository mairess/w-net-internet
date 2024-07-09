package com.maires.wnet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maires.wnet.controller.dto.MessagingNewInstallationDto;
import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Equipment;
import com.maires.wnet.entity.Installation;
import com.maires.wnet.entity.Plan;
import com.maires.wnet.entity.Technician;
import com.maires.wnet.producer.NewInstallationProducer;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.repository.InstallationRepository;
import com.maires.wnet.repository.PlanRepository;
import com.maires.wnet.repository.TechnicianRepository;
import com.maires.wnet.service.exception.AddressAlreadyAssociatedException;
import com.maires.wnet.service.exception.AddressNotFoundException;
import com.maires.wnet.service.exception.EquipmentAlreadyAssociatedException;
import com.maires.wnet.service.exception.EquipmentNotFoundException;
import com.maires.wnet.service.exception.PlanNotFoundException;
import com.maires.wnet.service.exception.TechnicianNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Address service.
 */
@Service
public class AddressService {

  private final AddressRepository addressRepository;
  private final EquipmentRepository equipmentRepository;
  private final PlanRepository planRepository;
  private final TechnicianRepository technicianRepository;
  private final InstallationRepository installationRepository;
  private final NewInstallationProducer newInstallationProducer;


  /**
   * Instantiates a new Address service.
   *
   * @param addressRepository       the address repository
   * @param equipmentRepository     the equipment repository
   * @param planRepository          the plan repository
   * @param technicianRepository    the technician repository
   * @param installationRepository  the installation repository
   * @param newInstallationProducer the new installation producer
   */
  @Autowired
  public AddressService(
      AddressRepository addressRepository,
      EquipmentRepository equipmentRepository,
      PlanRepository planRepository,
      TechnicianRepository technicianRepository,
      InstallationRepository installationRepository,
      NewInstallationProducer newInstallationProducer
  ) {
    this.addressRepository = addressRepository;
    this.equipmentRepository = equipmentRepository;
    this.planRepository = planRepository;
    this.technicianRepository = technicianRepository;
    this.installationRepository = installationRepository;
    this.newInstallationProducer = newInstallationProducer;
  }

  /**
   * Find all addresses list.
   *
   * @return the list
   */
  public List<Address> findAllAddresses() {
    return addressRepository.findAll();
  }

  /**
   * Find address by id address.
   *
   * @param addressId the address id
   * @return the address
   * @throws AddressNotFoundException the address not found exception
   */
  public Address findAddressById(Long addressId) throws AddressNotFoundException {
    return addressRepository.findById(addressId)
        .orElseThrow(() -> new AddressNotFoundException(addressId.toString()));
  }

  /**
   * Create address installation installation.
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
   * @throws JsonProcessingException             the json processing exception
   */
  @Transactional
  public Installation createAddressInstallation(
      Long addressId,
      Long planId,
      Long technicianId,
      List<Long> equipmentIds
  ) throws
      AddressNotFoundException,
      AddressAlreadyAssociatedException,
      PlanNotFoundException,
      TechnicianNotFoundException,
      EquipmentNotFoundException,
      EquipmentAlreadyAssociatedException, JsonProcessingException {

    Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> new AddressNotFoundException(addressId.toString()));

    if (address.getInstallation() != null) {
      throw new AddressAlreadyAssociatedException();
    }

    Plan plan = planRepository.findById(planId)
        .orElseThrow(() -> new PlanNotFoundException(planId.toString()));

    Technician technician = technicianRepository.findById(technicianId)
        .orElseThrow(() -> new TechnicianNotFoundException(technicianId.toString()));

    List<Equipment> equipmentList = new ArrayList<>();

    for (Long id : equipmentIds) {

      Equipment equipment = equipmentRepository.findById(id)
          .orElseThrow(() -> new EquipmentNotFoundException(id.toString()));

      if (equipment.getInstallation() != null) {
        throw new EquipmentAlreadyAssociatedException();
      }
      equipmentList.add(equipment);
    }

    Installation newInstallation = new Installation(address, plan, technician, equipmentList);
    address.setInstallation(newInstallation);

    equipmentList.forEach(equipment -> equipment.setInstallation(newInstallation));

    MessagingNewInstallationDto message = new MessagingNewInstallationDto(
        address.getCustomer().getEmail(),
        address.getCustomer().getFullName().split(" ")[0]

    );

    newInstallationProducer.sendMessage(message);

    return installationRepository.save(newInstallation);
  }

  /**
   * Update address.
   *
   * @param addressId the address id
   * @param address   the address
   * @return the address
   * @throws AddressNotFoundException the address not found exception
   */
  public Address updateAddress(Long addressId, Address address) throws AddressNotFoundException {
    Address addressToUpdate = findAddressById(addressId);

    addressToUpdate.setCity(address.getCity());
    addressToUpdate.setState(address.getState());
    addressToUpdate.setZipCode(address.getZipCode());
    addressToUpdate.setStreet(addressToUpdate.getStreet());
    addressToUpdate.setStreetNumber(address.getStreetNumber());
    addressToUpdate.setNeighborhood(address.getNeighborhood());
    addressToUpdate.setComplement(address.getComplement());

    return addressRepository.save(addressToUpdate);
  }


  /**
   * Remove address by id address.
   *
   * @param addressId the address id
   * @return the address
   * @throws AddressNotFoundException the address not found exception
   */
  @Transactional
  public Address removeAddressById(Long addressId) throws AddressNotFoundException {
    Address deletedAddress = findAddressById(addressId);

    if (deletedAddress.getInstallation() != null) {
      List<Equipment> equipmentList = deletedAddress.getInstallation().getEquipments();

      equipmentList.forEach(equipment -> {
        equipment.setInstallation(null);
        equipmentRepository.save(equipment);
      });

    }
    addressRepository.delete(deletedAddress);
    return deletedAddress;
  }
}