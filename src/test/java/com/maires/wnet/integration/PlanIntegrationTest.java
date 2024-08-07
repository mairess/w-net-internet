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
@DisplayName("Plan integration tests")
public class PlanIntegrationTest {

  @Container
  public static PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer("postgres")
      .withDatabaseName("wnetdb");
  @Container
  public static KafkaContainer KAFKA_CONTAINER = new KafkaContainer();
  @Autowired
  PlanRepository planRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  AddressRepository addressRepository;
  @Autowired
  TechnicianRepository technicianRepository;
  @Autowired
  EquipmentRepository equipmentRepository;
  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  InstallationRepository installationRepository;
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
    planRepository.deleteAll();
    User admin = new User(null, "System Manager Administrator", "admin@mail.com", "admin",
        "segredo123",
        Role.ADMIN);
    User userAdmin = userRepository.save(admin);
    tokenAdmin = tokenService.generateToken(userAdmin.getUsername());
  }

  @Test
  @DisplayName("Retrieval all plans")
  public void testPlanRetrievalAll() throws Exception {
    Plan planOne = new Plan("Speed of Light", 299, 100.0);
    Plan planTwe = new Plan("Low Connection", 10, 50.0);

    planRepository.save(planOne);
    planRepository.save(planTwe);
    String planUrl = "/plans";

    mockMvc.perform(get(planUrl)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].name").value("Speed of Light"))
        .andExpect(jsonPath("$[1].id").exists())
        .andExpect(jsonPath("$[1].name").value("Low Connection"));
  }

  @Test
  @DisplayName("Retrieval plan by id")
  public void testPlanRetrievalById() throws Exception {
    Plan plan = new Plan("Speed of Light", 299, 100.0);

    planRepository.save(plan);
    String planUrl = "/plans/%s".formatted(plan.getId());

    mockMvc.perform(get(planUrl)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("Speed of Light"))
        .andExpect(jsonPath("$.speed").value(299))
        .andExpect(jsonPath("$.price").value(100.0));
  }

  @Test
  @DisplayName("Throw planNotFoundException")
  public void testPlanNotFoundExceptionById() throws Exception {

    String planUrl = "/plans/666";

    mockMvc.perform(get(planUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Plan not found with identifier 666!"));
  }

  @Test
  @DisplayName("Create plan")
  public void testCreatePlan() throws Exception {

    Plan newPlan = new Plan("Speed of Light", 299, 100.0);

    ObjectMapper objectMapper = new ObjectMapper();
    String newPlanJson = objectMapper.writeValueAsString(newPlan);
    String planUrl = "/plans";

    mockMvc.perform(post(planUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(newPlanJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("Speed of Light"))
        .andExpect(jsonPath("$.speed").value(299))
        .andExpect(jsonPath("$.price").value(100.0));
  }

  @Test
  @DisplayName("Update plan")
  public void testUpdatePlan() throws Exception {

    Plan planToUpdate = new Plan("Speed of Light", 299, 100.0);
    planRepository.save(planToUpdate);

    planToUpdate.setName("Speed of Thunder");
    planToUpdate.setSpeed(150);
    planToUpdate.setPrice(70.0);

    ObjectMapper objectMapper = new ObjectMapper();
    String updatedPlanJson = objectMapper.writeValueAsString(planToUpdate);
    String planUrl = "/plans/%s".formatted(planToUpdate.getId());

    mockMvc.perform(put(planUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(updatedPlanJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("Speed of Thunder"))
        .andExpect(jsonPath("$.speed").value(150))
        .andExpect(jsonPath("$.price").value(70.0));
  }

  @Test
  @DisplayName("Delete plan")
  public void testDeletePlan() throws Exception {

    Plan planToDelete = new Plan("Speed of Light", 299, 100.0);
    planRepository.save(planToDelete);

    String planUrl = "/plans/%s".formatted(planToDelete.getId());

    mockMvc.perform(delete(planUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("Speed of Light"))
        .andExpect(jsonPath("$.speed").value(299))
        .andExpect(jsonPath("$.price").value(100.0));
  }

  @Test
  @DisplayName("Throw planCannotBeExcludedException")
  public void testPlanCannotBeExcludedException() throws Exception {

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

    String planUrl = "/plans/%s".formatted(plan.getId());

    mockMvc.perform(delete(planUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message").value(
            "This Plan cannot be excluded because it has an association!"));
  }

}