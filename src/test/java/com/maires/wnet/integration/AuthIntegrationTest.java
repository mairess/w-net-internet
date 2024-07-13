package com.maires.wnet.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maires.wnet.controller.dto.AuthDto;
import com.maires.wnet.entity.User;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.repository.InstallationRepository;
import com.maires.wnet.repository.PlanRepository;
import com.maires.wnet.repository.TechnicianRepository;
import com.maires.wnet.repository.UserRepository;
import com.maires.wnet.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DisplayName("Authentication integration tests")
public class AuthIntegrationTest {

  @Container
  public static PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer("postgres")
      .withDatabaseName("wnetdb");
  @Container
  public static KafkaContainer KAFKA_CONTAINER = new KafkaContainer();

  @Autowired
  UserRepository userRepository;

  @Autowired
  PlanRepository planRepository;

  @Autowired
  InstallationRepository installationRepository;

  @Autowired
  TechnicianRepository technicianRepository;

  @Autowired
  EquipmentRepository equipmentRepository;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @DynamicPropertySource
  public static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
  }

  @BeforeEach
  public void cleanUp() {
    userRepository.deleteAll();
    installationRepository.deleteAll();
    technicianRepository.deleteAll();
    planRepository.deleteAll();
    equipmentRepository.deleteAll();

    User admin = new User(
        null,
        "System Manager Administrator",
        "admin@mail.com",
        "admin",
        passwordEncoder.encode("segredo123"),
        Role.ADMIN);

    userRepository.save(admin);
  }

  @Test
  @DisplayName("Login successfully")
  public void testAuthentication() throws Exception {

    AuthDto login = new AuthDto("admin", "segredo123");

    ObjectMapper objectMapper = new ObjectMapper();
    String loginJson = objectMapper.writeValueAsString(login);
    String authUrl = "/auth/login";

    mockMvc.perform(post(authUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(loginJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists());
  }

}