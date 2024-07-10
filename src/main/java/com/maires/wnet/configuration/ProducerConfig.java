package com.maires.wnet.configuration;

import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


/**
 * The type Producer config.
 */
@Configuration
public class ProducerConfig {

  private final KafkaProperties kafkaProperties;

  @Value("${kafka.topic}")
  private String newInstallationRequest;

  /**
   * Instantiates a new Producer config.
   *
   * @param kafkaProperties the kafka properties
   */
  @Autowired
  public ProducerConfig(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  /**
   * Producer factory producer factory.
   *
   * @return the producer factory
   */
  @Bean
  public ProducerFactory<String, String> producerFactory() {
    Map<String, Object> properties = kafkaProperties.buildProducerProperties();
    return new DefaultKafkaProducerFactory<>(properties);
  }

  /**
   * Kafka template kafka template.
   *
   * @return the kafka template
   */
  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  /**
   * Messaging request topic build new topic.
   *
   * @return the new topic
   */
  @Bean
  public NewTopic messagingRequestTopicBuild() {
    return TopicBuilder.name(newInstallationRequest).partitions(1).replicas(1).build();
  }

}