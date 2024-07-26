package com.maires.wnet.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
  Enum validator
  Created by: [Walefy Gonçalves]
  GitHub: [https://github.com/walefy]
  Email: [walefyd1@gmail.com]
  Date: [07/25/2024]
*/

/**
 * The interface Enum validator.
 */
@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface EnumValidator {

  /**
   * Message string.
   *
   * @return the string
   */
  String message() default "Value is not valid";

  /**
   * Groups class [ ].
   *
   * @return the class [ ]
   */
  Class<?>[] groups() default {};

  /**
   * Enum clazz class.
   *
   * @return the class
   */
  Class<? extends Enum<?>> enumClazz();

  /**
   * Payload class [ ].
   *
   * @return the class [ ]
   */
  Class<? extends Payload>[] payload() default {};

}