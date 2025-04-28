package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.WalletEntity;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.repository.WalletRepository;
import by.yayauheny.util.TestDataUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
class WalletRepositoryIT extends IntegrationBaseTest {

  private final UserRepository userRepository;
  private final WalletRepository walletRepository;

  @Test
  void save_validUserWallet_saved() {
    var userWallet = createAndSaveWallet();

    var savedWallet = walletRepository.findById(userWallet.getId()).get();
    assertThat(savedWallet.getId()).isEqualTo(userWallet.getId());
  }

  @Test
  void findById_walletNotExist_notFound() {
    var walletId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundWallet = walletRepository.findById(walletId);

    assertThat(foundWallet).isEmpty();
  }

  @Test
  void findById_walletExist_foundAll() {
    var userWallet = createAndSaveWallet();

    var savedWallet = walletRepository.findById(userWallet.getId()).get();

    assertThat(savedWallet.getId()).isEqualTo(userWallet.getId());
  }

  @Test
  void delete_walletExist_deleted() {
    var userWallet = createAndSaveWallet();
    var savedWallet = walletRepository.findById(userWallet.getId()).get();

    walletRepository.delete(savedWallet);

    entityManager.flush();
    entityManager.clear();
    var foundWallet = walletRepository.findById(savedWallet.getId());
    assertThat(foundWallet).isEmpty();
  }

  @Test
  void update_updatedWalletCurrency_updated() {
    var userWallet = createAndSaveWallet();
    var savedWallet = walletRepository.findById(userWallet.getId()).get();
    var updatedCurrency = "PLN";
    savedWallet.setCurrency(updatedCurrency);

    walletRepository.save(savedWallet);

    entityManager.flush();
    entityManager.clear();
    var updatedWallet = walletRepository.findById(savedWallet.getId()).get();
    assertThat(updatedWallet.getCurrency()).isEqualTo(updatedCurrency);
  }

  private WalletEntity createAndSaveWallet() {
    var user = TestDataUtil.getUser("john.doe@example.com");
    var userWallet = TestDataUtil.getWallet(user);
    userRepository.save(user);
    var savedWallet = walletRepository.save(userWallet);
    entityManager.flush();
    entityManager.clear();

    return savedWallet;
  }
}