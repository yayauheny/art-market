package by.yayauheny.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.yayauheny.dto.UserSaveRequest;
import by.yayauheny.dto.UserUpdateRequest;
import by.yayauheny.enums.Role;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.integration.annotation.EnableTestcontainers;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.util.TestDataUtil;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@EnableTestcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class UserRestControllerIT extends IntegrationBaseTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;


  @Nested
  @DisplayName("check for user find flow")
  class UserFindTest {

    @Test
    void findAllUsers_usersExist_returnsUserList() throws Exception {
      var user = TestDataUtil.getUser("ex1@mail.ru");
      userRepository.saveAndFlush(user);

      mockMvc.perform(get("/api/v1/users")
              .param("page", "0")
              .param("size", "1")
          )
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content[0].id").value(user.getId().toString()));
    }

    @Test
    void findAllUsers_usersNotExist_returnsEmptyList() throws Exception {
      mockMvc.perform(get("/api/v1/users"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    void findUserById_userExists_returnsUserDetails() throws Exception {
      var user = TestDataUtil.getUser("ex1@mail.ru");
      userRepository.saveAndFlush(user);

      mockMvc.perform(get("/api/v1/users/" + user.getId()))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(user.getId().toString()));
    }

    @Test
    void findUserById_userNotExist_returnsNotFound() throws Exception {
      UUID nonExistingId = UUID.fromString("a8dad1ea-dc7a-4943-8d46-6f566f5b3085");

      mockMvc.perform(get("/api/v1/users/" + nonExistingId))
          .andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("check for user create or update flow")
  class UserUpsertTest {

    @Test
    void save_validRequest_returnsUserResponse() throws Exception {
      var userSaveRequest = new UserSaveRequest("ex1@mail.ru",
          "Nikolay",
          "Ivanov",
          "some_password",
          Role.SELLER,
          "Novosibirskaya street 3, 22, Moscow",
          LocalDate.of(1993, 2, 14)
      );

      mockMvc.perform(post("/api/v1/users")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(userSaveRequest))
              .characterEncoding(StandardCharsets.UTF_8)
          )
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.email").value(userSaveRequest.email()));
    }

    @Test
    void update_validRequest_returnsUserResponse() throws Exception {
      var user = TestDataUtil.getUser("ex1@mail.ru");
      var savedUser = userRepository.saveAndFlush(user);
      var userId = savedUser.getId();
      var userUpdateRequest = new UserUpdateRequest("Nikolay",
          "Ivanov",
          Role.SELLER,
          "Novosibirskaya street 3, 22, Moscow",
          LocalDate.of(1993, 2, 14)
      );

      mockMvc.perform(put("/api/v1/users/" + userId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(userUpdateRequest))
              .characterEncoding(StandardCharsets.UTF_8)
          )
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(userId.toString()))
          .andExpect(jsonPath("$.address").value(userUpdateRequest.address()));
    }
  }
}
