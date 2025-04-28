package by.yayauheny.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.integration.annotation.EnableTestcontainers;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.util.TestDataUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@EnableTestcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class UserRestControllerIT extends IntegrationBaseTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Test
  void findAllUsers_whenUsersExist_thenReturnUserList() throws Exception {
    var user = TestDataUtil.getUser("ex1@mail.ru");
    userRepository.saveAndFlush(user);

    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].id").value(user.getId().toString()));
  }

  @Test
  void findAllUsers_whenNoUsersExist_thenReturnEmptyList() throws Exception {
    userRepository.deleteAll();

    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  void findUserById_whenUserExists_thenReturnUserDetails() throws Exception {
    var user = TestDataUtil.getUser("ex1@mail.ru");
    userRepository.saveAndFlush(user);

    mockMvc.perform(get("/users/" + user.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(user.getId().toString()));
  }

  @Test
  void findUserById_whenUserDoesNotExist_thenReturnNotFound() throws Exception {
    UUID nonExistingId = UUID.fromString("a8dad1ea-dc7a-4943-8d46-6f566f5b3085");

    mockMvc.perform(get("/users/" + nonExistingId))
        .andExpect(status().isNotFound());
  }
}
