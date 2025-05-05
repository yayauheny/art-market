package by.yayauheny.service;

import static by.yayauheny.entity.QUserEntity.userEntity;

import by.yayauheny.dto.UserResponse;
import by.yayauheny.dto.UserSaveRequest;
import by.yayauheny.dto.UserUpdateRequest;
import by.yayauheny.dto.filter.UserFilter;
import by.yayauheny.http.exception.NoSuchEntityException;
import by.yayauheny.mapper.UserMapper;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.repository.filter.QPredicate;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public Page<UserResponse> findAll(@NonNull UserFilter filter, Pageable pageable) {
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

  @Transactional
  public UserResponse save(UserSaveRequest request) {
    return Optional.of(request)
        .map(userMapper::toEntity)
        .map(userRepository::save)
        .map(userMapper::toResponse)
        .orElseThrow();
  }

  @Transactional
  public UserResponse update(UUID id, UserUpdateRequest request) {
    return userRepository.findById(id)
        .map(entity -> userMapper.toEntity(request, entity))
        .map(userRepository::save)
        .map(userMapper::toResponse)
        .orElseThrow();
  }
}
