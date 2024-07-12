package com.maires.wnet.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.maires.wnet.entity.Equipment;
import com.maires.wnet.entity.EquipmentType;
import com.maires.wnet.entity.User;
import com.maires.wnet.repository.EquipmentRepository;
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
@DisplayName("Equipment integration tests")
public class EquipmentIntegrationTest {

  @Container
  public static PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer("postgres")
      .withDatabaseName("wnetdb");
  @Container
  public static KafkaContainer kafkaContainer = new KafkaContainer();
  @Autowired
  EquipmentRepository equipmentRepository;
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
    equipmentRepository.deleteAll();
    User admin = new User(null, "System Manager Administrator", "admin@mail.com", "admin",
        "segredo123",
        Role.ADMIN);
    User userAdmin = userRepository.save(admin);
    tokenAdmin = tokenService.generateToken(userAdmin.getUsername());
  }

  @Test
  @DisplayName("Retrieval all equipments")
  public void testEquipmentRetrievalAll() throws Exception {
    Equipment router = new Equipment(EquipmentType.ROUTER, "TP-Link Deco M4", "SN5332", "TP-Link");
    Equipment modem = new Equipment(EquipmentType.MODEM, "Motorola MG7700", "SN5333", "Motorola");

    equipmentRepository.save(router);
    equipmentRepository.save(modem);
    String equipmentUrl = "/equipments";

    mockMvc.perform(get(equipmentUrl)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].model").value("TP-Link Deco M4"))
        .andExpect(jsonPath("$[1].id").exists())
        .andExpect(jsonPath("$[1].model").value("Motorola MG7700"));
  }

  @Test
  @DisplayName("Retrieval equipment by id")
  public void testEquipmentRetrievalById() throws Exception {
    Equipment router = new Equipment(EquipmentType.ROUTER, "TP-Link Deco M4", "SN5332", "TP-Link");

    equipmentRepository.save(router);
    String equipmentUrl = "/equipments/%s".formatted(router.getId());

    mockMvc.perform(get(equipmentUrl)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.model").value("TP-Link Deco M4"));
  }

  @Test
  @DisplayName("Create equipment")
  public void testCreateEquipment() throws Exception {

    Equipment newEquipment = new Equipment(EquipmentType.ROUTER, "TP-Link Deco M4", "SN5332",
        "TP-Link");

    ObjectMapper objectMapper = new ObjectMapper();
    String newEquipmentJson = objectMapper.writeValueAsString(newEquipment);
    String equipmentUrl = "/equipments";

    mockMvc.perform(post(equipmentUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(newEquipmentJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.type").value("ROUTER"))
        .andExpect(jsonPath("$.model").value("TP-Link Deco M4"))
        .andExpect(jsonPath("$.serialNumber").value("SN5332"))
        .andExpect(jsonPath("$.manufacturer").value("TP-Link"));
  }

  @Test
  @DisplayName("Update equipment")
  public void testUpdateEquipment() throws Exception {

    Equipment equipmentToUpdate = new Equipment(EquipmentType.ROUTER, "TP-Link Deco M4", "SN5332",
        "TP-Link");
    equipmentRepository.save(equipmentToUpdate);

    equipmentToUpdate.setType(EquipmentType.MODEM);
    equipmentToUpdate.setModel("Netgear Nighthawk AC1900");
    equipmentToUpdate.setManufacturer("Netgear");

    ObjectMapper objectMapper = new ObjectMapper();
    String updatedPlanJson = objectMapper.writeValueAsString(equipmentToUpdate);
    String equipmentUrl = "/equipments/%s".formatted(equipmentToUpdate.getId());

    mockMvc.perform(put(equipmentUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(updatedPlanJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.type").value("MODEM"))
        .andExpect(jsonPath("$.model").value("Netgear Nighthawk AC1900"))
        .andExpect(jsonPath("$.serialNumber").value("SN5332"))
        .andExpect(jsonPath("$.manufacturer").value("Netgear"));
  }

  @Test
  @DisplayName("Delete equipment")
  public void testDeleteEquipment() throws Exception {

    Equipment equipmentToDelete = new Equipment(EquipmentType.ROUTER, "TP-Link Deco M4", "SN5332",
        "TP-Link");
    equipmentRepository.save(equipmentToDelete);

    String equipmentUrl = "/equipments/%s".formatted(equipmentToDelete.getId());

    mockMvc.perform(delete(equipmentUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.type").value("ROUTER"))
        .andExpect(jsonPath("$.model").value("TP-Link Deco M4"))
        .andExpect(jsonPath("$.serialNumber").value("SN5332"))
        .andExpect(jsonPath("$.manufacturer").value("TP-Link"));
  }

}