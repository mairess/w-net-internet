package com.maires.wnet.integration;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.maires.wnet.entity.Address;
import com.maires.wnet.entity.Customer;
import com.maires.wnet.entity.User;
import com.maires.wnet.repository.AddressRepository;
import com.maires.wnet.repository.CustomerRepository;
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
@DisplayName("Customer integration tests")
public class CustomerIntegrationTest {

  @Container
  public static PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer("postgres")
      .withDatabaseName("wnetdb");
  @Container
  public static KafkaContainer KAFKA_CONTAINER = new KafkaContainer();
  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  AddressRepository addressRepository;
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
    registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
  }

  @BeforeEach
  public void cleanUp() {
    userRepository.deleteAll();
    customerRepository.deleteAll();
    User admin = new User(null, "System Manager Administrator", "admin@mail.com", "admin",
        "segredo123",
        Role.ADMIN);
    User userAdmin = userRepository.save(admin);
    tokenAdmin = tokenService.generateToken(userAdmin.getUsername());
  }

  @Test
  @DisplayName("Retrieval all Customers")
  public void testCustomerRetrievalAll() throws Exception {
    Customer everaldo = new Customer("Everaldo Soares Cordeiro", "00011122233", "77000001111",
        "everaldo@mail.com");
    Customer machado = new Customer("Joaquim Maria Machado de Assis", "93684193020",
        "77009901111",
        "machado@mail.com");

    customerRepository.save(everaldo);
    customerRepository.save(machado);
    String customerUrl = "/customers";

    mockMvc.perform(get(customerUrl)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].fullName").value("Everaldo Soares Cordeiro"))
        .andExpect(jsonPath("$[1].id").exists())
        .andExpect(jsonPath("$[1].fullName").value("Joaquim Maria Machado de Assis"));
  }

  @Test
  @DisplayName("Retrieval customer by id")
  public void testCustomerRetrievalById() throws Exception {
    Customer machado = new Customer("Joaquim Maria Machado de Assis", "93684193020",
        "77009901111",
        "machado@mail.com");

    customerRepository.save(machado);
    String customerUrl = "/customers/%s".formatted(machado.getId());

    mockMvc.perform(get(customerUrl)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Joaquim Maria Machado de Assis"))
        .andExpect(jsonPath("$.cpf").value("93684193020"))
        .andExpect(jsonPath("$.phone").value("77009901111"))
        .andExpect(jsonPath("$.email").value("machado@mail.com"));
  }

  @Test
  @DisplayName("Retrieval all addresses by customer")
  public void testAddressesRetrievalByCustomer() throws Exception {
    Customer machado = new Customer("Joaquim Maria Machado de Assis", "93684193020",
        "77009901111",
        "machado@mail.com");

    customerRepository.save(machado);

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

    home.setCustomer(machado);
    work.setCustomer(machado);
    addressRepository.save(home);
    addressRepository.save(work);

    String customerUrl = "/customers/%s/addresses".formatted(machado.getId());

    mockMvc.perform(get(customerUrl)
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
  @DisplayName("Create customer")
  public void testCreateCustomer() throws Exception {

    Customer newCustomer = new Customer("Joaquim Maria Machado de Assis", "93684193020",
        "77009901111",
        "machado@mail.com");

    ObjectMapper objectMapper = new ObjectMapper();
    String newCustomerJson = objectMapper.writeValueAsString(newCustomer);
    String customerUrl = "/customers";

    mockMvc.perform(post(customerUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(newCustomerJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Joaquim Maria Machado de Assis"))
        .andExpect(jsonPath("$.cpf").value("93684193020"))
        .andExpect(jsonPath("$.phone").value("77009901111"))
        .andExpect(jsonPath("$.email").value("machado@mail.com"));
  }

  @Test
  @DisplayName("Update customer")
  public void testUpdateCustomer() throws Exception {

    Customer customerToUpdate = new Customer("Joaquim Maria Machado de Assis", "93684193020",
        "77009901111",
        "machadodexango@mail.com");

    customerRepository.save(customerToUpdate);

    customerToUpdate.setFullName("Joaquim Maria Machado de Assis de Xangô");
    customerToUpdate.setEmail("machadodexango@mail.com");

    ObjectMapper objectMapper = new ObjectMapper();
    String updatedCustomerJson = objectMapper.writeValueAsString(customerToUpdate);
    String customerUrl = "/customers/%s".formatted(customerToUpdate.getId());

    mockMvc.perform(put(customerUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(updatedCustomerJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Joaquim Maria Machado de Assis de Xangô"))
        .andExpect(jsonPath("$.cpf").value("93684193020"))
        .andExpect(jsonPath("$.phone").value("77009901111"))
        .andExpect(jsonPath("$.email").value("machadodexango@mail.com"));
  }

  @Test
  @DisplayName("Create customer address")
  public void testCreateCustomerAddress() throws Exception {

    Customer customer = new Customer("Joaquim Maria Machado de Assis", "93684193020",
        "77009901111",
        "machado@mail.com");

    customerRepository.save(customer);

    Address newAddress = new Address(
        "São Paulo",
        "São Paulo",
        "01000000",
        "Avenida Paulista",
        1023,
        "Bela Vista",
        "Próximo ao Parque Trianon"

    );

    ObjectMapper objectMapper = new ObjectMapper();
    String newAddressJson = objectMapper.writeValueAsString(newAddress);
    String customerUrl = "/customers/%s/addresses".formatted(customer.getId());

    mockMvc.perform(post(customerUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(newAddressJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.city").value("São Paulo"))
        .andExpect(jsonPath("$.state").value("São Paulo"))
        .andExpect(jsonPath("$.zipCode").value("01000000"))
        .andExpect(jsonPath("$.street").value("Avenida Paulista"))
        .andExpect(jsonPath("$.streetNumber").value(1023))
        .andExpect(jsonPath("$.neighborhood").value("Bela Vista"))
        .andExpect(jsonPath("$.complement").value("Próximo ao Parque Trianon"));
  }

  @Test
  @DisplayName("Delete Customer")
  public void testDeleteCustomer() throws Exception {

    Customer customerToDelete = new Customer("Gilvan Carlos Ferreira", "93684193020",
        "77009901111",
        "ferreira@mail.com");
    customerRepository.save(customerToDelete);

    String customerUrl = "/customers/%s".formatted(customerToDelete.getId());

    mockMvc.perform(delete(customerUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Gilvan Carlos Ferreira"))
        .andExpect(jsonPath("$.cpf").value("93684193020"))
        .andExpect(jsonPath("$.phone").value("77009901111"))
        .andExpect(jsonPath("$.email").value("ferreira@mail.com"));
  }

}