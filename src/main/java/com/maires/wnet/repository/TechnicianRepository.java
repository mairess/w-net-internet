package com.maires.wnet.repository;

import com.maires.wnet.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Technician repository.
 */
public interface TechnicianRepository extends JpaRepository<Technician, Long> {

}