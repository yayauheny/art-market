package by.yayauheny.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import by.yayauheny.dto.UserResponse;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.exception.NoSuchEntityException;
import by.yayauheny.mapper.UserMapper;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.util.TestDataUtil;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  UserRepository userRepository;
  UserMapper userMapper = Mappers.getMapper(UserMapper.class);
  UserService userService;

  @BeforeEach
  void setUp() {
    userMapper = Mappers.getMapper(UserMapper.class);
    userService = new UserService(userRepository, userMapper);
  }

  @Test
  void findAll_noUsers_returnEmptyList() {
    doReturn(Collections.emptyList()).when(userRepository).findAll();

    List<UserResponse> foundUsers = userService.findAll();

    verify(userRepository).findAll();
    assertThat(foundUsers).isEmpty();
  }

  @Test
  void findAll_usersExists_returnAllUsers() {
    List<UserEntity> users = List.of(
        TestDataUtil.getUser("ex1@mail.ru"),
        TestDataUtil.getUser("ex2@mail.ru"),
        TestDataUtil.getUser("ex3@mail.ru")
    );

    doReturn(users).when(userRepository).findAll();

    List<UserResponse> foundUsers = userService.findAll();

    verify(userRepository).findAll();
    assertThat(foundUsers).hasSize(users.size());
  }

  @Test
  void findById_userNotExist_throwNoSuchEntityException() {
    var id = UUID.fromString("57f23e0b-bdb6-454b-b5a8-82493b9f963a");
    doReturn(Optional.empty()).when(userRepository).findById(any());

    Assertions.assertThrows(NoSuchEntityException.class, () ->
        userService.findById(id)
    );
    verify(userRepository).findById(id);
  }

  @Test
  void findById_userExist_returnUserResponse() {
    var userId = UUID.fromString("57f23e0b-bdb6-454b-b5a8-82493b9f963a");
    var user = TestDataUtil.getUser("ex1@mail.ru").toBuilder()
        .id(userId)
        .build();
    doReturn(Optional.of(user)).when(userRepository).findById(any());

    UserResponse foundUserResponse = userService.findById(userId);

    assertThat(foundUserResponse).isNotNull()
        .extracting(UserResponse::id)
        .isEqualTo(userId);
    verify(userRepository).findById(userId);
  }
}