package com.maires.wnet.service;

import com.maires.wnet.entity.User;
import com.maires.wnet.repository.UserRepository;
import com.maires.wnet.service.exception.UserNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * The type User service.
 */
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;


  /**
   * Instantiates a new User service.
   *
   * @param userRepository the user repository
   */
  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Find all users list.
   *
   * @return the list
   */
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }


  /**
   * Find user by id user.
   *
   * @param id the id
   * @return the user
   * @throws UserNotFoundException the user not found exception
   */
  public User findUserById(Long id) throws UserNotFoundException {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
  }


  /**
   * Find user by username user.
   *
   * @param username the username
   * @return the user
   * @throws UserNotFoundException the user not found exception
   */
  public User findUserByUsername(String username) throws UserNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  /**
   * Create user.
   *
   * @param user the user
   * @return the user
   */
  public User createUser(User user) {
    String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
    user.setPassword(hashedPassword);
    return userRepository.save(user);
  }

  /**
   * Update user.
   *
   * @param userId the user id
   * @param user   the user
   * @return the user
   * @throws UserNotFoundException the user not found exception
   */
  public User updateUser(Long userId, User user) throws UserNotFoundException {
    User userToUpdate = findUserById(userId);

    userToUpdate.setFullName(user.getFullName());
    userToUpdate.setEmail(user.getEmail());
    userToUpdate.setUsername(userToUpdate.getUsername());
    userToUpdate.setPassword(userToUpdate.getPassword());
    userToUpdate.setRole(user.getRole());

    return userRepository.save(userToUpdate);
  }

  /**
   * Remove user user.
   *
   * @param userId the user id
   * @return the user
   * @throws UserNotFoundException the user not found exception
   */
  public User removeUserById(Long userId) throws UserNotFoundException {
    User userDeleted = findUserById(userId);
    userRepository.delete(userDeleted);
    return userDeleted;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}