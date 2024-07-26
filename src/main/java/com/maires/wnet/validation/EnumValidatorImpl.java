package com.maires.wnet.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/*
  Enum validator
  Created by: [Walefy Gonçalves]
  GitHub: [https://github.com/walefy]
  Email: [walefyd1@gmail.com]
  Date: [07/25/2024]
*/

/**
 * The type Enum validator.
 */
public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

  /**
   * The Value list.
   */
  final List<String> valueList = new ArrayList<>();

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value != null && valueList.contains(value.toUpperCase());
  }

  @Override
  public void initialize(EnumValidator constraintAnnotation) {
    Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

    Enum<?>[] enumValArr = enumClass.getEnumConstants();

    for (Enum<?> enumVal : enumValArr) {
      valueList.add(enumVal.toString().toUpperCase());
    }
  }

}