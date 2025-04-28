package by.yayauheny.service;

import static by.yayauheny.entity.QUserEntity.userEntity;

import by.yayauheny.dto.UserResponse;
import by.yayauheny.dto.filter.UserFilter;
import by.yayauheny.http.exception.NoSuchEntityException;
import by.yayauheny.mapper.UserMapper;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.repository.filter.QPredicate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public Page<UserResponse> findAll(UserFilter filter, Pageable pageable) {
    if (filter == null) {
      throw new IllegalArgumentException("Filter cannot be empty");
    }
    var predicate = QPredicate.builder()
        .add(filter.getName(), userEntity.name::likeIgnoreCase)
        .add(filter.getLastName(), userEntity.name::likeIgnoreCase)
        .add(filter.getRole(), userEntity.role::eq)
        .buildAnd();
    return userRepository.findAll(predicate, pageable)
        .map(userMapper::toResponse);
  }

  public UserResponse findById(UUID id) {
    return userRepository.findById(id)
        .map(userMapper::toResponse)
        .orElseThrow(() -> new NoSuchEntityException("User with id - %s not found".formatted(id)));
  }
}
