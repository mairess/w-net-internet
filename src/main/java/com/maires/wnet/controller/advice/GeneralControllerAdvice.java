package com.maires.wnet.controller.advice;

import com.maires.wnet.service.exception.NotFoundException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * The type General controller advice.
 */
@ControllerAdvice
public class GeneralControllerAdvice {


  /**
   * Handle not found response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException exception) {
    Map<String, String> response = Map.of("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}