package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.UserEntity;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.repository.WalletRepository;
import by.yayauheny.util.TestDataUtil;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
class UserRepositoryIT extends IntegrationBaseTest {

  private final UserRepository userRepository;
  private final WalletRepository walletRepository;

  @Test
  void save_validUserWithWallet_saved() {
    var user = createAndSaveUser();

    var savedUser = userRepository.findById(user.getId()).get();
    assertThat(savedUser.getId()).isEqualTo(user.getId());
  }

  @Test
  void findById_userNotExist_notFound() {
    var userId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundUser = userRepository.findById(userId);

    assertThat(foundUser).isEmpty();
  }

  @Test
  void findById_userExist_found() {
    var user = createAndSaveUser();

    var foundUser = userRepository.findById(user.getId()).get();

    assertThat(foundUser.getId()).isEqualTo(user.getId());
  }

  @Test
  void delete_userExist_deleted() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    userRepository.save(user);
    walletRepository.save(userWallet);
    entityManager.flush();
    entityManager.clear();
    var foundUser = userRepository.findById(user.getId()).get();
    var foundUserWallet = walletRepository.findById(userWallet.getId()).get();

    walletRepository.delete(foundUserWallet);
    userRepository.delete(foundUser);

    entityManager.flush();
    entityManager.clear();
    Optional<UserEntity> deletedUser = userRepository.findById(user.getId());
    assertThat(deletedUser).isEmpty();
  }

  @Test
  void update_updatedUserAddress_updated() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    var updatedAddress = "updated address";
    userRepository.save(user);
    walletRepository.save(userWallet);
    entityManager.flush();
    entityManager.clear();
    var savedUser = userRepository.findById(user.getId()).get();
    savedUser.setAddress(updatedAddress);

    userRepository.save(savedUser);

    entityManager.flush();
    entityManager.clear();
    var updatedUser = userRepository.findById(user.getId()).get();
    assertThat(updatedUser.getAddress()).isEqualTo(updatedAddress);
  }

  private UserEntity createAndSaveUser() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    var savedUser = userRepository.save(user);
    walletRepository.save(userWallet);
    entityManager.flush();
    entityManager.clear();

    return savedUser;
  }
}