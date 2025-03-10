package by.yayauheny.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.WalletEntity;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.util.TestDataUtil;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class WalletEntityIT extends IntegrationBaseTest {

  @Test
  void save_validUserWallet_saved() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);

    session.persist(user);
    session.persist(userWallet);
    session.flush();
    session.clear();
    var savedWallet = session.get(WalletEntity.class, userWallet.getId());

    assertThat(savedWallet).isNotNull();
  }

  @Test
  void findById_walletNotExist_notFound() {
    var walletId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundWallet = session.get(WalletEntity.class, walletId);

    assertThat(foundWallet).isNull();
  }

  @Test
  void findById_multipleWallets_foundAll() {
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

    var firstSavedWallet = session.get(WalletEntity.class, firstUserWallet.getId());
    var secondSavedWallet = session.get(WalletEntity.class, secondUserWallet.getId());

    assertThat(List.of(firstSavedWallet, secondSavedWallet))
        .allMatch(Objects::nonNull);
  }

  @Test
  void remove_walletExist_removed() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    session.persist(user);
    session.persist(userWallet);
    session.flush();
    session.clear();
    var savedWallet = session.get(WalletEntity.class, userWallet.getId());

    session.remove(savedWallet);
    session.flush();
    session.clear();
    var foundWallet = session.get(WalletEntity.class, savedWallet.getId());

    assertThat(foundWallet).isNull();
  }

  @Test
  void update_updatedWalletCurrency_updated() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    var updatedCurrency = "PLN";
    session.persist(user);
    session.persist(userWallet);
    session.flush();
    session.clear();
    var savedWallet = session.get(WalletEntity.class, userWallet.getId());

    savedWallet.setCurrency(updatedCurrency);
    session.merge(savedWallet);
    session.flush();
    session.clear();
    var updatedWallet = session.get(WalletEntity.class, savedWallet.getId());

    assertThat(updatedWallet)
        .isNotNull()
        .isEqualTo(savedWallet);
  }
}