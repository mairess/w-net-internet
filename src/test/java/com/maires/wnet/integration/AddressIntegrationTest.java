package com.maires.wnet.integration;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maires.wnet.controller.dto.InstallationCreationDto;
import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Customer;
import com.maires.wnet.entity.Equipment;
import com.maires.wnet.entity.EquipmentType;
import com.maires.wnet.entity.Plan;
import com.maires.wnet.entity.Technician;
import com.maires.wnet.entity.User;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.CustomerRepository;
import com.maires.wnet.repository.EquipmentRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DisplayName("Address integration tests")
public class AddressIntegrationTest {

  @Container
  public static PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer("postgres")
      .withDatabaseName("wnetdb");
  @Container
  public static KafkaContainer kafkaContainer = new KafkaContainer();
  @Autowired
  AddressRepository addressRepository;
  @Autowired
  TechnicianRepository technicianRepository;
  @Autowired
  EquipmentRepository equipmentRepository;
  @Autowired
  PlanRepository planRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  CustomerRepository customerRepository;
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
    addressRepository.deleteAll();
    User admin = new User(null, "System Manager Administrator", "admin@mail.com", "admin",
        "segredo123",
        Role.ADMIN);
    User userAdmin = userRepository.save(admin);
    tokenAdmin = tokenService.generateToken(userAdmin.getUsername());
  }

  @Test
  @DisplayName("Retrieval all addresses")
  public void testAddressRetrievalAll() throws Exception {
    Address home = new Address(
        "São Paulo",
        "São Paulo",
        "01000000",
        "Avenida Paulista",
        1023,
        "Próximo ao Parque Trianon",
        "Bela Vista"
    );

    Address work = new Address(
        "São Paulo",
        "São Paulo",
        "13000000",
        "Fazenda Santo Antônio",
        null,
        "Próximo ao Lago Azul",
        "Zona Rural"
    );

    addressRepository.save(home);
    addressRepository.save(work);
    String addressUrl = "/addresses";

    mockMvc.perform(get(addressUrl)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].street").value("Avenida Paulista"))
        .andExpect(jsonPath("$[0].streetNumber").value(1023))
        .andExpect(jsonPath("$[1].street").value("Fazenda Santo Antônio"))
        .andExpect(jsonPath("$[1].streetNumber").value(nullValue()));
  }

  @Test
  @DisplayName("Retrieval customer by id")
  public void testCustomerRetrievalById() throws Exception {
    Address home = new Address(
        "São Paulo",
        "São Paulo",
        "01000000",
        "Avenida Paulista",
        1023,
        "Bela Vista",
        "Próximo ao Parque Trianon"
    );

    addressRepository.save(home);
    String addressUrl = "/addresses/%s".formatted(home.getId());

    mockMvc.perform(get(addressUrl)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.city").value("São Paulo"))
        .andExpect(jsonPath("$.state").value("São Paulo"))
        .andExpect(jsonPath("$.zipCode").value("01000000"))
        .andExpect(jsonPath("$.street").value("Avenida Paulista"))
        .andExpect(jsonPath("$.streetNumber").value(1023))
        .andExpect(jsonPath("$.neighborhood").value("Bela Vista"))
        .andExpect(jsonPath("$.complement").value("Próximo ao Parque Trianon"));
  }

  @Test
  @DisplayName("Create address installation")
  public void testCreateAddressInstallation() throws Exception {

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

    List<Long> equipmentList = new ArrayList<>();
    equipmentList.add(router.getId());
    equipmentList.add(modem.getId());

    InstallationCreationDto installationDto = new InstallationCreationDto(plan.getId(),
        technician.getId(), equipmentList);

    ObjectMapper objectMapper = new ObjectMapper();
    String newInstallationJson = objectMapper.writeValueAsString(installationDto);
    String addressUrl = "/addresses/%s/installations".formatted(home.getId());

    mockMvc.perform(post(addressUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(newInstallationJson))
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Update address")
  public void testUpdateAddress() throws Exception {

    Address addressToUpdate = new Address(
        "São Paulo", "São Paulo", "01000000", "Avenida Paulista", 1023, "Bela Vista",
        "Próximo ao Parque Trianon");

    addressRepository.save(addressToUpdate);

    addressToUpdate.setCity("Guarulhos");
    addressToUpdate.setZipCode("11111111");
    addressToUpdate.setStreet("Rua das Flores");
    addressToUpdate.setStreetNumber(41);
    addressToUpdate.setNeighborhood("Jardim Verde");

    ObjectMapper objectMapper = new ObjectMapper();
    String updatedAddressJson = objectMapper.writeValueAsString(addressToUpdate);
    String addressUrl = "/addresses/%s".formatted(addressToUpdate.getId());

    mockMvc.perform(put(addressUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(updatedAddressJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.city").value("Guarulhos"))
        .andExpect(jsonPath("$.zipCode").value("11111111"))
        .andExpect(jsonPath("$.street").value("Rua das Flores"))
        .andExpect(jsonPath("$.streetNumber").value(41))
        .andExpect(jsonPath("$.neighborhood").value("Jardim Verde"));
  }

  @Test
  @DisplayName("Delete Customer")
  public void testDeleteEquipment() throws Exception {

    Address addressToDelete = new Address(
        "São Paulo", "São Paulo", "01000000", "Avenida Paulista", 1023, "Bela Vista",
        "Próximo ao Parque Trianon");

    addressRepository.save(addressToDelete);

    String addressUrl = "/addresses/%s".formatted(addressToDelete.getId());

    mockMvc.perform(delete(addressUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.city").value("São Paulo"))
        .andExpect(jsonPath("$.zipCode").value("01000000"))
        .andExpect(jsonPath("$.street").value("Avenida Paulista"))
        .andExpect(jsonPath("$.streetNumber").value(1023))
        .andExpect(jsonPath("$.neighborhood").value("Bela Vista"))
        .andExpect(jsonPath("$.complement").value("Próximo ao Parque Trianon"));
  }

}