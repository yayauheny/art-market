package by.yayauheny.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import by.yayauheny.dto.UserResponse;
import by.yayauheny.dto.UserSaveRequest;
import by.yayauheny.dto.UserUpdateRequest;
import by.yayauheny.dto.filter.UserFilter;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.Role;
import by.yayauheny.http.exception.NoSuchEntityException;
import by.yayauheny.mapper.UserMapper;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.util.TestDataUtil;
import com.querydsl.core.types.Predicate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserService userService;

  @Nested
  @DisplayName("check for user find flow")
  class UserFindTest {

    @Test
    void findAll_noUsers_returnsEmptyList() {
      var usersFilter = UserFilter.builder().build();
      var pageRequest = PageRequest.of(1, 10);
      doReturn(Page.empty()).when(userRepository)
          .findAll(any(Predicate.class), any(Pageable.class));

      var foundUsers = userService.findAll(usersFilter, pageRequest);

      verify(userRepository).findAll(any(Predicate.class), any(Pageable.class));
      assertThat(foundUsers).isEmpty();
    }

    @Test
    void findAll_usersExists_returnsAllUsers() {
      var userEntities = List.of(
          TestDataUtil.getUser("ex1@mail.ru"),
          TestDataUtil.getUser("ex2@mail.ru"),
          TestDataUtil.getUser("ex3@mail.ru")
      );
      var usersFilter = UserFilter.builder().build();
      var pageRequest = PageRequest.of(1, 10);
      Page<UserEntity> usersPage = new PageImpl<>(userEntities, pageRequest, userEntities.size());
      doReturn(usersPage).when(userRepository).findAll(any(Predicate.class), any(Pageable.class));

      Page<UserResponse> foundUsers = userService.findAll(usersFilter, pageRequest);

      verify(userRepository).findAll(any(Predicate.class), any(Pageable.class));
      assertThat(foundUsers).hasSize(userEntities.size());
    }

    @Test
    void findById_userNotExist_throwsNoSuchEntityException() {
      var id = UUID.fromString("57f23e0b-bdb6-454b-b5a8-82493b9f963a");
      doReturn(Optional.empty()).when(userRepository).findById(any());

      assertThrows(NoSuchEntityException.class, () -> userService.findById(id));
      verify(userRepository).findById(id);
    }

    @Test
    void findById_userExist_returnsUserResponse() {
      var userId = UUID.fromString("57f23e0b-bdb6-454b-b5a8-82493b9f963a");
      var user = TestDataUtil.getUser("ex1@mail.ru").toBuilder()
          .id(userId)
          .build();
      var userResponse = TestDataUtil.toUserResponse(user);
      doReturn(Optional.of(user)).when(userRepository).findById(userId);
      doReturn(userResponse).when(userMapper).toResponse(user);

      UserResponse foundUserResponse = userService.findById(userId);

      assertThat(foundUserResponse).isNotNull()
          .extracting(UserResponse::id)
          .isEqualTo(userId);
      verify(userRepository).findById(userId);
    }
  }

  @Nested
  @DisplayName("check for user create or update flow")
  class UserUpsertTest {

    @Test
    void save_validUserSave_returnsSavedUserEntity() {
      var userId = UUID.fromString("57f23e0b-bdb6-454b-b5a8-82493b9f963a");
      var hashedPassword = "hashed rawPassword";
      var userSaveRequest = new UserSaveRequest("ex1@mail.ru",
          "Nikolay",
          "Ivanov",
          "some_password",
          Role.SELLER,
          "Novosibirskaya street 3, 22, Moscow",
          LocalDate.of(1993, 2, 14)
      );
      var user = TestDataUtil.toUserEntity(userSaveRequest).toBuilder()
          .id(userId)
          .password(hashedPassword)
          .build();
      var userResponse = TestDataUtil.toUserResponse(user);
      doReturn(userResponse).when(userMapper).toResponse(user);
      doReturn(user).when(userMapper).toEntity(userSaveRequest);
      doReturn(user).when(userRepository).save(user);

      var savedUserResponse = userService.save(userSaveRequest);

      assertThat(savedUserResponse).isNotNull()
          .extracting(UserResponse::id)
          .isEqualTo(userId);
      verify(userRepository).save(user);
    }

    @Test
    void update_validUserUpdate_returnsUpdatedUserEntity() {
      var userId = UUID.fromString("57f23e0b-bdb6-454b-b5a8-82493b9f963a");
      var updateRequest = new UserUpdateRequest("Alex",
          "Sokolov",
          Role.USER,
          "some address",
          LocalDate.of(1993, 2, 14)
      );
      var user = TestDataUtil.getUser("ex1@mail.ru").toBuilder()
          .id(userId)
          .build();
      var updatedUser = TestDataUtil.updateEntity(user, updateRequest);
      var userResponse = TestDataUtil.toUserResponse(user);
      doReturn(updatedUser).when(userMapper).toEntity(updateRequest, user);
      doReturn(userResponse).when(userMapper).toResponse(user);
      doReturn(Optional.of(updatedUser)).when(userRepository).findById(userId);
      doReturn(updatedUser).when(userRepository).save(user);

      var savedUserResponse = userService.update(userId, updateRequest);

      assertThat(savedUserResponse).isNotNull()
          .extracting(UserResponse::id)
          .isEqualTo(userId);
      verify(userRepository).save(user);
    }
  }
}