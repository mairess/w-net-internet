package com.maires.wnet.repository;

import com.maires.wnet.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Person repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find by username optional.
   *
   * @param username the username
   * @return the optional
   */
  Optional<User> findByUsername(String username);

}