package com.maires.wnet.repository;

import com.maires.wnet.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Address repository.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}