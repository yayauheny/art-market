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
import by.yayauheny.service.validation.UserValidationService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserValidationService userValidationService;
  private final UserMapper userMapper;

  public Page<UserResponse> findAll(@NonNull UserFilter filter, Pageable pageable) {
    var predicate = QPredicate.builder()
        .add(filter.getName(), userEntity.name::containsIgnoreCase)
        .add(filter.getLastName(), userEntity.lastName::containsIgnoreCase)
        .add(filter.getRole(), userEntity.role::eq)
        .buildAnd();
    return userRepository.findAll(predicate, pageable)
        .map(userMapper::toResponse);
  }

  public List<UserResponse> findAll() {
    return Optional.of(userRepository.findAll())
        .map(userMapper::toResponseList)
        .orElseThrow();
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
        .map(entity -> {
          userValidationService.throwIfExistsByEmail(entity.getEmail());
          return entity;
        })
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
        .orElseThrow(() -> new NoSuchEntityException("User with id - %s not found".formatted(id)));
  }

  @Transactional
  public void delete(UUID id) {
    userRepository.findById(id)
        .ifPresentOrElse(
            entity -> {
              userRepository.delete(entity);
              userRepository.flush();
            },
            () -> {
              throw new NoSuchEntityException("User with id - %s not found".formatted(id));
            }
        );
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByName(username)
        .map(entity -> new User(entity.getEmail(),
            entity.getPassword(),
            Collections.singleton(entity.getRole()))
        )
        .orElseThrow(() -> new UsernameNotFoundException("Failed to fetch user with username: " + username));
  }
}
