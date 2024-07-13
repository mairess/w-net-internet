package com.maires.wnet.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maires.wnet.controller.dto.InstallationCreationDto;
import com.maires.wnet.entity.Address;
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

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DisplayName("Installation integration tests")
public class InstallationIntegrationTest {

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
  InstallationRepository installationRepository;
  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  TechnicianRepository technicianRepository;
  @Autowired
  AddressRepository addressRepository;
  @Autowired
  EquipmentRepository equipmentRepository;
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
    planRepository.deleteAll();
    equipmentRepository.deleteAll();
    User admin = new User(null, "System Manager Administrator", "admin@mail.com", "admin",
        "segredo123",
        Role.ADMIN);
    User userAdmin = userRepository.save(admin);
    tokenAdmin = tokenService.generateToken(userAdmin.getUsername());
  }

  @Test
  @DisplayName("Retrieval all installations")
  public void testInstallationRetrievalAll() throws Exception {

    Address home = new Address(
        "São Paulo", "São Paulo", "01000000", "Avenida Paulista", 1023, "Bela Vista",
        "Próximo ao Parque Trianon");

    Address work = new Address(
        "Campinas", "São Paulo", "02000022", "Avenida Rosalita Fagundes", 152, "Bosque",
        "Ao lado da padaria");

    Technician technician = new Technician("João Antônio Benevides Faria", "77011114444",
        "joao@example.com");

    Plan plan = new Plan("Speed og Thunder", 300, 70.0);

    Equipment router = new Equipment(EquipmentType.ROUTER, "Asus RT-AC88U", "SN035", "Asus");
    Equipment modem = new Equipment(EquipmentType.MODEM, "Motorola MG7700", "SN036", "Motorola");

    Equipment routerWork = new Equipment(EquipmentType.ROUTER, "Asus RT-AC88U", "SN099", "Asus");
    Equipment modemWork = new Equipment(EquipmentType.MODEM, "Motorola MG7700", "SN098",
        "Motorola");

    addressRepository.save(home);
    addressRepository.save(work);
    technicianRepository.save(technician);
    planRepository.save(plan);
    equipmentRepository.save(router);
    equipmentRepository.save(modem);
    equipmentRepository.save(routerWork);
    equipmentRepository.save(modemWork);

    List<Equipment> equipmentListHome = new ArrayList<>();
    equipmentListHome.add(router);
    equipmentListHome.add(modem);

    List<Equipment> equipmentListWork = new ArrayList<>();
    equipmentListWork.add(router);
    equipmentListWork.add(modem);

    Installation newInstallationHome = new Installation(home, plan, technician, equipmentListHome);
    Installation newInstallationWork = new Installation(work, plan, technician, equipmentListWork);

    installationRepository.save(newInstallationHome);
    installationRepository.save(newInstallationWork);

    String installationUrl = "/installations";

    mockMvc.perform(get(installationUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].isActive").value(true))
        .andExpect(jsonPath("$[0].id").value(newInstallationHome.getId()))
        .andExpect(jsonPath("$[1].isActive").value(true))
        .andExpect(jsonPath("$[1].id").value(newInstallationWork.getId()));
  }

  @Test
  @DisplayName("Retrieval installation by id")
  public void testInstallationRetrievalById() throws Exception {
    Address home = new Address(
        "São Paulo", "São Paulo", "01000000", "Avenida Paulista", 1023, "Bela Vista",
        "Próximo ao Parque Trianon");

    Technician technician = new Technician("João Antônio Benevides Faria", "77011114444",
        "joao@example.com");

    Plan plan = new Plan("Speed og Thunder", 300, 70.0);

    Equipment router = new Equipment(EquipmentType.ROUTER, "Asus RT-AC88U", "SN035", "Asus");
    Equipment modem = new Equipment(EquipmentType.MODEM, "Motorola MG7700", "SN036", "Motorola");

    addressRepository.save(home);
    technicianRepository.save(technician);
    planRepository.save(plan);
    equipmentRepository.save(router);
    equipmentRepository.save(modem);

    List<Equipment> equipmentListHome = new ArrayList<>();
    equipmentListHome.add(router);
    equipmentListHome.add(modem);

    Installation newInstallationHome = new Installation(home, plan, technician, equipmentListHome);

    installationRepository.save(newInstallationHome);

    String installationUrl = "/installations/%s".formatted(newInstallationHome.getId());

    mockMvc.perform(get(installationUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(newInstallationHome.getId()))
        .andExpect(jsonPath("$.isActive").value(true))
        .andExpect(jsonPath("$.plan").exists())
        .andExpect(jsonPath("$.technician").exists())
        .andExpect(jsonPath("$.equipments").exists());
  }

  @Test
  @DisplayName("Update installation")
  public void testUpdateInstallation() throws Exception {

    Address home = new Address(
        "São Paulo", "São Paulo", "01000000", "Avenida Paulista", 1023, "Bela Vista",
        "Próximo ao Parque Trianon");

    Technician technician = new Technician("João Antônio Benevides Faria", "77011114444",
        "joao@example.com");

    Plan plan = new Plan("Speed og Thunder", 300, 70.0);

    Equipment router = new Equipment(EquipmentType.ROUTER, "Asus RT-AC88U", "SN035", "Asus");
    Equipment modem = new Equipment(EquipmentType.MODEM, "Motorola MG7700", "SN036", "Motorola");

    addressRepository.save(home);
    technicianRepository.save(technician);
    planRepository.save(plan);
    equipmentRepository.save(router);
    equipmentRepository.save(modem);

    List<Equipment> equipmentListHome = new ArrayList<>();
    equipmentListHome.add(router);
    equipmentListHome.add(modem);

    List<Long> blankList = new ArrayList<>();

    Installation installationToUpdate = new Installation(home, plan, technician, equipmentListHome);
    installationRepository.save(installationToUpdate);

    InstallationCreationDto installationDto = new InstallationCreationDto(plan.getId(),
        technician.getId(), blankList);

    ObjectMapper objectMapper = new ObjectMapper();
    String updatedInstallationJson = objectMapper.writeValueAsString(installationDto);

    String installationUrl = "/installations/%s".formatted(installationToUpdate.getId());

    mockMvc.perform(put(installationUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(updatedInstallationJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.equipments").isEmpty());
  }

  @Test
  @DisplayName("Delete installation")
  public void testDeleteInstallation() throws Exception {

    Address home = new Address(
        "São Paulo", "São Paulo", "01000000", "Avenida Paulista", 1023, "Bela Vista",
        "Próximo ao Parque Trianon");

    Technician technician = new Technician("João Antônio Benevides Faria", "77011114444",
        "joao@example.com");

    Plan plan = new Plan("Speed og Thunder", 300, 70.0);

    Equipment router = new Equipment(EquipmentType.ROUTER, "Asus RT-AC88U", "SN035", "Asus");
    Equipment modem = new Equipment(EquipmentType.MODEM, "Motorola MG7700", "SN036", "Motorola");

    addressRepository.save(home);
    technicianRepository.save(technician);
    planRepository.save(plan);
    equipmentRepository.save(router);
    equipmentRepository.save(modem);

    List<Equipment> equipmentListHome = new ArrayList<>();
    equipmentListHome.add(router);
    equipmentListHome.add(modem);

    Installation installationToDelete = new Installation(home, plan, technician, equipmentListHome);
    installationRepository.save(installationToDelete);

    String installationUrl = "/installations/%s".formatted(installationToDelete.getId());

    mockMvc.perform(delete(installationUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(installationToDelete.getId()))
        .andExpect(jsonPath("$.plan").exists())
        .andExpect(jsonPath("$.technician").exists())
        .andExpect(jsonPath("$.equipments").exists());
    ;
  }

}