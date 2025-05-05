package by.yayauheny.service.validation;

import by.yayauheny.http.exception.EntityAlreadyExists;
import by.yayauheny.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService {

  private final UserRepository userRepository;

  public void throwIfExistsByEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new EntityAlreadyExists(String.format("User with email: %s already exists", email));
    }
  }
}
