package com.maires.wnet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = {"installation-completed"})
class WnetInternetApplicationTests {


  private final EmbeddedKafkaBroker embeddedKafkaBroker;

  @Autowired
  WnetInternetApplicationTests(EmbeddedKafkaBroker embeddedKafkaBroker) {
    this.embeddedKafkaBroker = embeddedKafkaBroker;
  }

  @Test
  void contextLoads() {
  }

}