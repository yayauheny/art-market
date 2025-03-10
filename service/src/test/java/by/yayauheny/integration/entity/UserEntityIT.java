package by.yayauheny.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.UserEntity;
import by.yayauheny.entity.WalletEntity;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.util.TestDataUtil;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserEntityIT extends IntegrationBaseTest {

  @Test
  void save_validUserWithWallet_saved() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);

    session.persist(user);
    session.persist(userWallet);
    session.flush();
    session.clear();
    var savedUser = session.get(UserEntity.class, user.getId());

    assertThat(savedUser).isNotNull();
  }

  @Test
  void findById_userNotExist_notFound() {
    var userId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundUser = session.get(UserEntity.class, userId);

    assertThat(foundUser).isNull();
  }

  @Test
  void findById_multipleUsers_foundAll() {
    var firstUser = TestDataUtil.getUser("john.doe@example.com");
    var secondUser = TestDataUtil.getUser("john2.doe@example.com");
    var firstUserWallet = TestDataUtil.getWallet(firstUser);
    var secondUserWallet = TestDataUtil.getWallet(secondUser);
    session.persist(firstUser);
    session.persist(secondUser);
    session.persist(firstUserWallet);
    session.persist(secondUserWallet);
    session.flush();
    session.clear();

    var firstSavedUser = session.get(UserEntity.class, firstUser.getId());
    var secondSavedUser = session.get(UserEntity.class, secondUser.getId());

    assertThat(List.of(firstSavedUser, secondSavedUser))
        .allMatch(Objects::nonNull);
  }

  @Test
  void remove_userExist_removed() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    session.persist(user);
    session.persist(userWallet);
    session.flush();
    session.clear();
    var savedUser = session.get(UserEntity.class, user.getId());
    var savedWallet = session.get(WalletEntity.class, userWallet.getId());

    session.remove(savedWallet);
    session.remove(savedUser);
    session.flush();
    session.clear();
    var foundUser = session.get(UserEntity.class, savedUser.getId());

    assertThat(foundUser).isNull();
  }

  @Test
  void update_updatedUserAddress_updated() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    var updatedAddress = "updated address";
    session.persist(user);
    session.persist(userWallet);
    session.flush();
    session.clear();
    var savedUser = session.get(UserEntity.class, user.getId());

    savedUser.setAddress(updatedAddress);
    session.merge(savedUser);
    session.flush();
    session.clear();
    var updatedUser = session.get(UserEntity.class, savedUser.getId());

    assertThat(updatedUser)
        .isNotNull()
        .isEqualTo(savedUser);
  }
}