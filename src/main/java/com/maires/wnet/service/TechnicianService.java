package com.maires.wnet.service;

import com.maires.wnet.entity.Technician;
import com.maires.wnet.repository.TechnicianRepository;
import com.maires.wnet.service.exception.TechnicianCannotBeExcludedException;
import com.maires.wnet.service.exception.TechnicianNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Technician service.
 */
@Service
public class TechnicianService {

  private final TechnicianRepository technicianRepository;

  /**
   * Instantiates a new Technician service.
   *
   * @param technicianRepository the technician repository
   */
  @Autowired
  public TechnicianService(TechnicianRepository technicianRepository) {
    this.technicianRepository = technicianRepository;
  }

  /**
   * Find all technicians list.
   *
   * @return the list
   */
  public List<Technician> findAllTechnicians() {
    return technicianRepository.findAll();
  }

  /**
   * Find technician by id technician.
   *
   * @param technicianId the technician id
   * @return the technician
   * @throws TechnicianNotFoundException the technician not found exception
   */
  public Technician findTechnicianById(Long technicianId) throws TechnicianNotFoundException {
    return technicianRepository.findById(technicianId)
        .orElseThrow(TechnicianNotFoundException::new);
  }

  /**
   * Create technician.
   *
   * @param technician the technician
   * @return the technician
   */
  public Technician createTechnician(Technician technician) {
    return technicianRepository.save(technician);
  }


  /**
   * Remove technician by id technician.
   *
   * @param technicianId the technician id
   * @return the technician
   * @throws TechnicianNotFoundException         the technician not found exception
   * @throws TechnicianCannotBeExcludedException the technician cannot be excluded exception
   */
  public Technician removeTechnicianById(Long technicianId)
      throws TechnicianNotFoundException, TechnicianCannotBeExcludedException {
    Technician deletedTechnician = findTechnicianById(technicianId);

    if (!deletedTechnician.getInstallations().isEmpty()) {
      throw new TechnicianCannotBeExcludedException();
    }

    technicianRepository.delete(deletedTechnician);
    return deletedTechnician;
  }

  /**
   * Update technician technician.
   *
   * @param technicianId the technician id
   * @param technician   the technician
   * @return the technician
   * @throws TechnicianNotFoundException the technician not found exception
   */
  public Technician updateTechnician(Long technicianId, Technician technician)
      throws TechnicianNotFoundException {

    Technician technicianToUpdate = findTechnicianById(technicianId);

    technicianToUpdate.setName(technician.getName());
    technicianToUpdate.setPhone(technician.getPhone());
    technicianToUpdate.setEmail(technician.getEmail());

    return technicianRepository.save(technicianToUpdate);
  }
}