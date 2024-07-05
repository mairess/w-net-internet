package com.maires.wnet.controller;

import com.maires.wnet.controller.dto.PersonCreationDto;
import com.maires.wnet.controller.dto.PersonDto;
import com.maires.wnet.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Person controller.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

  private final PersonService personService;


  /**
   * Instantiates a new Person controller.
   *
   * @param personService the person service
   */
  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }


  /**
   * Create person dto.
   *
   * @param personCreationDto the person creation dto
   * @return the person dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Creates a person", description = "Cretes new user/person")
  @ApiResponse(
      responseCode = "201",
      description = "Created Person",
      content = @Content(schema = @Schema(implementation = PersonDto.class)))
  public PersonDto createPerson(@RequestBody PersonCreationDto personCreationDto) {
    return PersonDto.fromEntity(
        personService.create(personCreationDto.toEntity())
    );
  }
}