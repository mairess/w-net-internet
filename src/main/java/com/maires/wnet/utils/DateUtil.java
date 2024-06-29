package com.maires.wnet.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The type Date util.
 */
public class DateUtil {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "dd-MM-yyyy'T'HH:mm:ss");

  /**
   * Date util string.
   *
   * @return the string
   */
  public static String formatCurrentDate() {
    LocalDateTime now = LocalDateTime.now().minusHours(3);
    return now.format(formatter);
  }
}