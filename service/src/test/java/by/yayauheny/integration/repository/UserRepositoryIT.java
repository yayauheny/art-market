package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.UserEntity;
import by.yayauheny.entity.WalletEntity;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.repository.WalletRepository;
import by.yayauheny.util.TestDataUtil;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRepositoryIT extends IntegrationBaseTest {

  private UserRepository userRepository;
  private WalletRepository walletRepository;

  @BeforeEach
  void initDependencies() {
    userRepository = new UserRepository(session);
    walletRepository = new WalletRepository(session);
  }

  @Test
  void save_validUserWithWallet_saved() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);

    userRepository.save(user);
    walletRepository.save(userWallet);
    session.flush();
    session.clear();
    var savedUser = userRepository.findById(user.getId());

    assertThat(savedUser).isNotNull();
  }

  @Test
  void findById_userNotExist_notFound() {
    var userId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundUser = userRepository.findById(userId);

    assertThat(foundUser).isEmpty();
  }

  @Test
  void findById_userExist_found() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    userRepository.save(user);
    walletRepository.save(userWallet);
    session.flush();
    session.clear();

    Optional<UserEntity> foundUser = userRepository.findById(user.getId());

    assertThat(foundUser).isPresent();
  }

  @Test
  void findAll_multipleUsersExist_foundAll() {
    var firstUser = TestDataUtil.getUser("john.doe@example.com");
    var secondUser = TestDataUtil.getUser("john2.doe@example.com");
    var firstUserWallet = TestDataUtil.getWallet(firstUser);
    var secondUserWallet = TestDataUtil.getWallet(secondUser);
    userRepository.save(firstUser);
    userRepository.save(secondUser);
    walletRepository.save(firstUserWallet);
    walletRepository.save(secondUserWallet);
    session.flush();
    session.clear();

    List<UserEntity> foundUsers = userRepository.findAll();

    assertThat(foundUsers).hasSize(2);
  }

  @Test
  void remove_userExist_removed() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    UserEntity savedUser = userRepository.save(user);
    WalletEntity savedWallet = walletRepository.save(userWallet);
    session.flush();
    session.clear();
    Optional<UserEntity> foundUser = userRepository.findById(user.getId());
    Optional<WalletEntity> foundUserWallet = walletRepository.findById(userWallet.getId());

    foundUserWallet.ifPresent(w -> walletRepository.delete(savedWallet.getId()));
    foundUser.ifPresent(u -> userRepository.delete(savedUser.getId()));
    session.clear();
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
    session.flush();
    session.clear();
    Optional<UserEntity> savedUser = userRepository.findById(user.getId());

    savedUser.ifPresent(userForUpdate -> {
      userForUpdate.setAddress(updatedAddress);
      userRepository.update(userForUpdate);
      session.flush();
      session.clear();
    });
    Optional<UserEntity> updatedUser = userRepository.findById(user.getId());

    assertThat(updatedUser)
        .isPresent()
        .get()
        .extracting(UserEntity::getAddress)
        .isEqualTo(updatedAddress);
  }
}