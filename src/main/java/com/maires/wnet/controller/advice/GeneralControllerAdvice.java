package com.maires.wnet.controller.advice;

import com.maires.wnet.service.exception.AlreadyAssociatedException;
import com.maires.wnet.service.exception.CannotBeExcludedException;
import com.maires.wnet.service.exception.NotFoundException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  /**
   * Handle already associated response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(AlreadyAssociatedException.class)
  public ResponseEntity<Map<String, String>> handleAlreadyAssociated(
      AlreadyAssociatedException exception) {
    Map<String, String> response = Map.of("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  /**
   * Handle cannot be excluded exception response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(CannotBeExcludedException.class)
  public ResponseEntity<Map<String, String>> handleCannotBeExcluded(
      CannotBeExcludedException exception) {
    Map<String, String> response = Map.of("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  /**
   * Handle validation response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handleValidation(
      MethodArgumentNotValidException exception) {

    Map<String, List<String>> response = Map.of(
        "message",
        exception.getBindingResult()
            .getAllErrors()
            .stream()
            .map(error -> ((FieldError) error).getField() + " " + error.getDefaultMessage())
            .toList()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleInvalidArg(
      IllegalArgumentException exception) {
    Map<String, String> response = Map.of("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleInvalidArg(
      HttpMessageNotReadableException exception) {
    Map<String, String> response = Map.of("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

}