package com.maires.wnet.repository;

import com.maires.wnet.entity.Installation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Installation repository.
 */
@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {

}