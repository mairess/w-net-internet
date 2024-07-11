package com.maires.wnet.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.maires.wnet.entity.Technician;
import com.maires.wnet.entity.User;
import com.maires.wnet.repository.TechnicianRepository;
import com.maires.wnet.repository.UserRepository;
import com.maires.wnet.security.Role;
import com.maires.wnet.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DisplayName("Technician integration Tests")
public class TechnicianIntegrationTest {

  @Container
  public static PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer("postgres")
      .withDatabaseName("wnetdb");
  @Container
  public static KafkaContainer kafkaContainer = new KafkaContainer();
  @Autowired
  TechnicianRepository technicianRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  private TokenService tokenService;
  private String tokenAdmin;


  @DynamicPropertySource
  public static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
  }

  @DynamicPropertySource
  public static void kafkaProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
  }

  @BeforeEach
  public void cleanUp() {
    userRepository.deleteAll();
    technicianRepository.deleteAll();
    User admin = new User(null, "System Manager Administrator", "admin@mail.com", "admin",
        "segredo123",
        Role.ADMIN);
    User userAdmin = userRepository.save(admin);
    tokenAdmin = tokenService.generateToken(userAdmin.getUsername());
  }

  @Test
  @DisplayName("Retrieval all technicians")
  public void testTechnicianRetrievalAll() throws Exception {
    Technician Alberto = new Technician("Alberto Benevides de Castro", "7799000000000",
        "alberto@mail.com");
    Technician Amarildo = new Technician("Amarildo Xavier da Costa", "7799111111111",
        "amarildo@mail.com");

    technicianRepository.save(Alberto);
    technicianRepository.save(Amarildo);
    String technicianUrl = "/technicians";

    mockMvc.perform(get(technicianUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].fullName").value("Alberto Benevides de Castro"))
        .andExpect(jsonPath("$[1].id").exists())
        .andExpect(jsonPath("$[1].fullName").value("Amarildo Xavier da Costa"));
  }

  @Test
  @DisplayName("Retrieval technician by id")
  public void testTechnicianRetrievalById() throws Exception {
    Technician Amarildo = new Technician("Amarildo Xavier da Costa", "7799111111111",
        "amarildo@mail.com");
    technicianRepository.save(Amarildo);

    String technicianUrl = "/technicians/%s".formatted(Amarildo.getId());

    mockMvc.perform(get(technicianUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Amarildo Xavier da Costa"))
        .andExpect(jsonPath("$.phone").value("7799111111111"))
        .andExpect(jsonPath("$.email").value("amarildo@mail.com"));
  }

  @Test
  @DisplayName("Create technician")
  public void testCreateTechnician() throws Exception {

    Technician Amarildo = new Technician("Amarildo Xavier da Costa", "7799111111111",
        "amarildo@mail.com");

    ObjectMapper objectMapper = new ObjectMapper();
    String newTechnicianJson = objectMapper.writeValueAsString(Amarildo);
    String technicianUrl = "/technicians";

    mockMvc.perform(post(technicianUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(newTechnicianJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Amarildo Xavier da Costa"))
        .andExpect(jsonPath("$.phone").value("7799111111111"))
        .andExpect(jsonPath("$.email").value("amarildo@mail.com"));
  }

  @Test
  @DisplayName("Update technician")
  public void testUpdateTechnician() throws Exception {

    Technician technicianToUpdate = new Technician("Amarildo Xavier da Costa", "7799111111111",
        "amarildo@mail.com");
    technicianRepository.save(technicianToUpdate);

    technicianToUpdate.setFullName("Amarildo Candido Xavier");
    technicianToUpdate.setPhone("7799222222222");

    ObjectMapper objectMapper = new ObjectMapper();
    String updatedTechnicianJson = objectMapper.writeValueAsString(technicianToUpdate);
    String technicianUrl = "/technicians/%s".formatted(technicianToUpdate.getId());

    mockMvc.perform(put(technicianUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(updatedTechnicianJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Amarildo Candido Xavier"))
        .andExpect(jsonPath("$.phone").value("7799222222222"))
        .andExpect(jsonPath("$.email").value("amarildo@mail.com"));
  }

  @Test
  @DisplayName("Delete technician")
  public void testDeleteTechnician() throws Exception {

    Technician technicianToDelete = new Technician("Amarildo Xavier da Costa", "7799111111111",
        "amarildo@mail.com");

    technicianRepository.save(technicianToDelete);
    String technicianUrl = "/technicians/%s".formatted(technicianToDelete.getId());

    mockMvc.perform(delete(technicianUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Amarildo Xavier da Costa"))
        .andExpect(jsonPath("$.phone").value("7799111111111"))
        .andExpect(jsonPath("$.email").value("amarildo@mail.com"));
  }

}