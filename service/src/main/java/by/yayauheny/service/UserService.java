package by.yayauheny.service;

import by.yayauheny.dto.UserResponse;
import by.yayauheny.exception.NoSuchEntityException;
import by.yayauheny.mapper.UserMapper;
import by.yayauheny.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public List<UserResponse> findAll() {
    return userMapper.toResponseList(userRepository.findAll());
  }

  public UserResponse findById(UUID id) {
    return userRepository.findById(id)
        .map(userMapper::toResponse)
        .orElseThrow(() -> new NoSuchEntityException("User with id - %s not found".formatted(id)));
  }
}
