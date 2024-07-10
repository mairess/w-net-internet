package com.maires.wnet.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maires.wnet.controller.dto.MessagingNewInstallationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * The type New installation producer.
 */
@Service
public class NewInstallationProducer {

  private final ObjectMapper objectMapper;
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Value("${kafka.topic}")
  private String newInstallationRequest;

  /**
   * Instantiates a new New installation producer.
   *
   * @param objectMapper  the object mapper
   * @param kafkaTemplate the kafka template
   */
  @Autowired
  public NewInstallationProducer(
      ObjectMapper objectMapper,
      KafkaTemplate<String, String> kafkaTemplate
  ) {
    this.objectMapper = objectMapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Send message.
   *
   * @param messagingNewInstallationDto the messaging new installation dto
   * @throws JsonProcessingException the json processing exception
   */
  public void produceNewInstallationMessage(
      MessagingNewInstallationDto messagingNewInstallationDto
  ) throws JsonProcessingException {

    String content = objectMapper.writeValueAsString(messagingNewInstallationDto);
    kafkaTemplate.send(newInstallationRequest, content);
  }

}