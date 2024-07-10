package com.maires.wnet.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maires.wnet.controller.dto.MessagingNewInstallationDto;
import com.maires.wnet.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * The type New installation consumer.
 */
@Service
public class NewInstallationConsumer {

  private final EmailService emailService;
  private final ObjectMapper objectMapper;

  /**
   * Instantiates a new New installation consumer.
   *
   * @param emailService the email service
   * @param objectMapper the object mapper
   */
  @Autowired
  public NewInstallationConsumer(EmailService emailService, ObjectMapper objectMapper) {
    this.emailService = emailService;
    this.objectMapper = objectMapper;
  }

  /**
   * Consume installation message.
   *
   * @param message the message
   * @throws MessagingException      the messaging exception
   * @throws JsonProcessingException the json processing exception
   */
  @KafkaListener(
      topics = "${kafka.topic}",
      groupId = "message-request-consumer1"
  )
  public void consumeNewInstallationMessage(String message)
      throws MessagingException, JsonProcessingException {

    MessagingNewInstallationDto messagingNewInstallationDto = objectMapper.readValue(message,
        MessagingNewInstallationDto.class);

    emailService.sendNewInstallationMail(
        messagingNewInstallationDto.customerMail(),
        messagingNewInstallationDto.customerName()
    );

  }
}