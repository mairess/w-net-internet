package com.maires.wnet.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.maires.wnet.entity.User;
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
@DisplayName("User Integration Tests")
public class UserIntegrationTest {

  @Container
  public static PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer("postgres")
      .withDatabaseName("wnetdb");
  @Container
  public static KafkaContainer kafkaContainer = new KafkaContainer();
  @Autowired
  UserRepository userRepository;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  private TokenService tokenService;
  private User userAdmin;
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
    User admin = new User(null, "System Manager Administrator", "admin@mail.com", "admin",
        "segredo123",
        Role.ADMIN);
    userAdmin = userRepository.save(admin);
    tokenAdmin = tokenService.generateToken(userAdmin.getUsername());
  }

  @Test
  @DisplayName("Retrieval all users")
  public void testUserRetrievalAll() throws Exception {
    User Juca = new User(null, "Juca Rosa de Souza", "juquinha@mail.com", "juca", "segredo123",
        Role.TECHNICIAN);

    User Rogerio = new User(null, "Rogerio Cardoso da Sena", "rogerSena@mail.com", "rogerio",
        "segredo123",
        Role.TECHNICIAN);

    userRepository.save(Juca);
    userRepository.save(Rogerio);
    String userUrl = "/users";

    mockMvc.perform(get(userUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(3))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].fullName").value("System Manager Administrator"))
        .andExpect(jsonPath("$[1].id").exists())
        .andExpect(jsonPath("$[1].fullName").value("Juca Rosa de Souza"))
        .andExpect(jsonPath("$[2].id").exists())
        .andExpect(jsonPath("$[2].fullName").value("Rogerio Cardoso da Sena"));
  }

  @Test
  @DisplayName("Retrieval user by id")
  public void testUserRetrievalById() throws Exception {

    String userUrl = "/users/%s".formatted(userAdmin.getId());

    mockMvc.perform(get(userUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("System Manager Administrator"))
        .andExpect(jsonPath("$.email").value("admin@mail.com"))
        .andExpect(jsonPath("$.username").value("admin"))
        .andExpect(jsonPath("$.role").value("ADMIN"));
  }

  @Test
  @DisplayName("Retrieval user by username")
  public void testUserRetrievalByUsername() throws Exception {

    String userUrl = "/users/find?username=%s".formatted("admin");

    mockMvc.perform(get(userUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("System Manager Administrator"))
        .andExpect(jsonPath("$.email").value("admin@mail.com"))
        .andExpect(jsonPath("$.username").value("admin"))
        .andExpect(jsonPath("$.role").value("ADMIN"));
  }

  @Test
  @DisplayName("Create user")
  public void testCreateUser() throws Exception {

    User newUser = new User(null, "Edcarlos Saraiva Bragança", "edcarlos@mail.com", "edcarlos",
        "password123", Role.TECHNICIAN);

    ObjectMapper objectMapper = new ObjectMapper();
    String newUserJson = objectMapper.writeValueAsString(newUser);
    String userUrl = "/users";

    mockMvc.perform(post(userUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(newUserJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Edcarlos Saraiva Bragança"))
        .andExpect(jsonPath("$.email").value("edcarlos@mail.com"))
        .andExpect(jsonPath("$.username").value("edcarlos"))
        .andExpect(jsonPath("$.role").value("TECHNICIAN"));
  }

  @Test
  @DisplayName("Update user")
  public void testUpdateUser() throws Exception {

    User updatedUser = new User(null, "System Manager aka Administrator", "admin@mail.com", "admin",
        "segredo123",
        Role.ADMIN);

    ObjectMapper objectMapper = new ObjectMapper();
    String updatedUserJson = objectMapper.writeValueAsString(updatedUser);
    String userUrl = "/users/%s".formatted(userAdmin.getId());

    mockMvc.perform(put(userUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(updatedUserJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("System Manager aka Administrator"))
        .andExpect(jsonPath("$.email").value("admin@mail.com"))
        .andExpect(jsonPath("$.username").value("admin"))
        .andExpect(jsonPath("$.role").value("ADMIN"));
  }

  @Test
  @DisplayName("Delete user")
  public void testDeleteUser() throws Exception {

    User userToDelete = new User(null, "Please Remove Me From Your Database", "removeme@mail.com",
        "removeme",
        "segredo123",
        Role.TECHNICIAN);

    userRepository.save(userToDelete);
    String userUrl = "/users/%s".formatted(userToDelete.getId());

    mockMvc.perform(delete(userUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.fullName").value("Please Remove Me From Your Database"))
        .andExpect(jsonPath("$.email").value("removeme@mail.com"))
        .andExpect(jsonPath("$.username").value("removeme"))
        .andExpect(jsonPath("$.role").value("TECHNICIAN"));
  }


}