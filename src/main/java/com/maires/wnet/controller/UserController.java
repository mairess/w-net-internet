package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.UserCreationDto;
import com.maires.wnet.controller.dto.UserDto;
import com.maires.wnet.service.UserService;
import com.maires.wnet.service.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type User controller.
 */
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;


  /**
   * Instantiates a new User controller.
   *
   * @param userService the user service
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Find all users list.
   *
   * @return the list
   */
  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<UserDto> findAllUsers() {
    return userService.findAllUsers().stream().map(UserDto::fromEntity).toList();
  }

  /**
   * Find user by id user dto.
   *
   * @param userId the user id
   * @return the user dto
   * @throws UserNotFoundException the user not found exception
   */
  @GetMapping("/{userId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public UserDto findUserById(@PathVariable Long userId) throws UserNotFoundException {
    return UserDto.fromEntity(userService.findUserById(userId));
  }

  /**
   * Find user by username user dto.
   *
   * @param username the username
   * @return the user dto
   * @throws UserNotFoundException the user not found exception
   */
  @GetMapping("/find")
  @PreAuthorize("hasAuthority('ADMIN')")
  public UserDto findUserByUsername(@RequestParam String username) throws UserNotFoundException {
    return UserDto.fromEntity(userService.findUserByUsername(username));
  }

  /**
   * Create user user dto.
   *
   * @param userCreationDto the user creation dto
   * @return the user dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Creates an user", description = "Cretes new user")
  @ApiResponse(
      responseCode = "201",
      description = "Created user",
      content = @Content(schema = @Schema(implementation = UserDto.class)))
  public UserDto createUser(@RequestBody UserCreationDto userCreationDto) {
    return UserDto.fromEntity(userService.createUser(userCreationDto.toEntity()));
  }

  /**
   * Update user user dto.
   *
   * @param userId          the user id
   * @param userCreationDto the user creation dto
   * @return the user dto
   * @throws UserNotFoundException the user not found exception
   */
  @PreAuthorize("hasAuthority('ADMIN')")
  public UserDto updateUser(@PathVariable Long userId, UserCreationDto userCreationDto
  ) throws UserNotFoundException {
    return UserDto.fromEntity(userService.updateUser(userId, userCreationDto.toEntity()));
  }

  /**
   * Remove user by id user dto.
   *
   * @param userId the user id
   * @return the user dto
   * @throws UserNotFoundException the user not found exception
   */
  @DeleteMapping("/{userId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public UserDto removeUserById(@PathVariable Long userId) throws UserNotFoundException {
    return UserDto.fromEntity(userService.removeUserById(userId));
  }

}