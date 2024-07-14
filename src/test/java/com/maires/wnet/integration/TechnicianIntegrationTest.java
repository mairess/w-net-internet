package com.maires.wnet.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Customer;
import com.maires.wnet.entity.Equipment;
import com.maires.wnet.entity.EquipmentType;
import com.maires.wnet.entity.Installation;
import com.maires.wnet.entity.Plan;
import com.maires.wnet.entity.Technician;
import com.maires.wnet.entity.User;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.CustomerRepository;
import com.maires.wnet.repository.EquipmentRepository;
import com.maires.wnet.repository.InstallationRepository;
import com.maires.wnet.repository.PlanRepository;
import com.maires.wnet.repository.TechnicianRepository;
import com.maires.wnet.repository.UserRepository;
import com.maires.wnet.security.Role;
import com.maires.wnet.service.TokenService;
import java.util.ArrayList;
import java.util.List;
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
@DisplayName("Technician integration tests")
public class TechnicianIntegrationTest {

  @Container
  public static PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer("postgres")
      .withDatabaseName("wnetdb");
  @Container
  public static KafkaContainer KAFKA_CONTAINER = new KafkaContainer();
  @Autowired
  TechnicianRepository technicianRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  AddressRepository addressRepository;
  @Autowired
  EquipmentRepository equipmentRepository;
  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  InstallationRepository installationRepository;
  @Autowired
  PlanRepository planRepository;
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
    registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
  }

  @BeforeEach
  public void cleanUp() {
    userRepository.deleteAll();
    installationRepository.deleteAll();
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
  @DisplayName("Throw technicianNotFoundException")
  public void testTechnicianNotFoundExceptionById() throws Exception {

    String technicianUrl = "/technicians/666";

    mockMvc.perform(get(technicianUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Technician not found with identifier 666!"));
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

  @Test
  @DisplayName("Throw technicianCannotBeExcludedException")
  public void testTechnicianCannotBeExcludedException() throws Exception {

    Customer customer = new Customer(
        "Graciliano Ramos de Oliveira",
        "00011122233",
        "77011223344",
        "gracit@example.com"
    );

    Address home = new Address(
        "São Paulo", "São Paulo", "01000000", "Avenida Paulista", 1023, "Bela Vista",
        "Próximo ao Parque Trianon");

    Technician technician = new Technician("João Antônio Benevides Faria", "77011114444",
        "joao@example.com");

    Plan plan = new Plan("Speed og Thunder", 300, 70.0);

    Equipment router = new Equipment(EquipmentType.ROUTER, "Asus RT-AC88U", "SN035", "Asus");
    Equipment modem = new Equipment(EquipmentType.MODEM, "Motorola MG7700", "SN036", "Motorola");

    home.setCustomer(customer);
    customerRepository.save(customer);
    addressRepository.save(home);
    technicianRepository.save(technician);
    planRepository.save(plan);
    equipmentRepository.save(router);
    equipmentRepository.save(modem);

    List<Equipment> equipmentList = new ArrayList<>();
    equipmentList.add(router);
    equipmentList.add(modem);

    Installation installation = new Installation(home, plan, technician, equipmentList);

    installationRepository.save(installation);

    String technicianUrl = "/technicians/%s".formatted(technician.getId());

    mockMvc.perform(delete(technicianUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message").value(
            "This Technician cannot be excluded because he has an association!"));
  }

}