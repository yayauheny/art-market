package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.BidHoldBalanceEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.BidHoldBalanceStatus;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.integration.annotation.IT;
import by.yayauheny.repository.AuctionRepository;
import by.yayauheny.repository.BidHoldBalanceRepository;
import by.yayauheny.repository.BidRepository;
import by.yayauheny.repository.CategoryRepository;
import by.yayauheny.repository.ItemRepository;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.repository.WalletRepository;
import by.yayauheny.util.TestDataUtil;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@IT
@RequiredArgsConstructor
class BidHoldBalanceRepositoryIT extends IntegrationBaseTest {

  private final UserRepository userRepository;
  private final WalletRepository walletRepository;
  private final ItemRepository itemRepository;
  private final CategoryRepository categoryRepository;
  private final AuctionRepository auctionRepository;
  private final BidRepository bidRepository;
  private final BidHoldBalanceRepository bidHoldBalanceRepository;

  @Test
  void save_validBidHoldBalance_saved() {
    var bidHoldBalance = createAndSaveBidHoldBalance();

    var savedBidHoldBalance = bidHoldBalanceRepository.findById(bidHoldBalance.getId()).get();
    assertThat(savedBidHoldBalance.getId()).isEqualTo(bidHoldBalance.getId());
  }

  @Test
  void findById_bidHoldBalanceNotExist_notFound() {
    var bidHoldBalanceId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundBidHoldBalance = bidHoldBalanceRepository.findById(bidHoldBalanceId);

    assertThat(foundBidHoldBalance).isEmpty();
  }

  @Test
  void findById_bidHoldBalanceExist_found() {
    var bidHoldBalance = createAndSaveBidHoldBalance();

    var savedBidHoldBalance = bidHoldBalanceRepository.findById(bidHoldBalance.getId()).get();

    assertThat(savedBidHoldBalance.getId()).isEqualTo(bidHoldBalance.getId());
  }

  @Test
  void delete_bidHoldBalanceExist_deleted() {
    var bidHoldBalance = createAndSaveBidHoldBalance();
    var savedBidHoldBalance = bidHoldBalanceRepository.findById(bidHoldBalance.getId()).get();

    bidHoldBalanceRepository.delete(savedBidHoldBalance);

    entityManager.flush();
    entityManager.clear();
    var foundBidHoldBalance = bidHoldBalanceRepository.findById(savedBidHoldBalance.getId());
    assertThat(foundBidHoldBalance).isEmpty();
  }

  @Test
  void update_updatedBidHoldBalanceStatus_updated() {
    var bidHoldBalance = createAndSaveBidHoldBalance();
    var savedBidHoldBalance = bidHoldBalanceRepository.findById(bidHoldBalance.getId()).get();
    BidHoldBalanceStatus updatedStatus = BidHoldBalanceStatus.CONFIRMED;
    savedBidHoldBalance.setStatus(updatedStatus);

    bidHoldBalanceRepository.save(savedBidHoldBalance);

    entityManager.flush();
    entityManager.clear();
    var updatedBidHoldBalance = bidHoldBalanceRepository.findById(bidHoldBalance.getId()).get();
    assertThat(updatedBidHoldBalance.getStatus()).isEqualTo(updatedStatus);
  }

  private BidHoldBalanceEntity createAndSaveBidHoldBalance() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity bidOwner = TestDataUtil.getUser("rich.kid@example.com");
    var bidOwnerWallet = TestDataUtil.getWallet(bidOwner);
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    var bid = TestDataUtil.getBid(auction, bidOwner, BigDecimal.TEN);
    var bidHoldBalance = TestDataUtil.getBidHoldBalance(bidOwnerWallet, auction, bid);
    userRepository.save(seller);
    userRepository.save(bidOwner);
    walletRepository.save(bidOwnerWallet);
    categoryRepository.save(category);
    itemRepository.save(item);
    auctionRepository.save(auction);
    bidRepository.save(bid);
    var savedBidHoldBalance = bidHoldBalanceRepository.save(bidHoldBalance);
    entityManager.flush();
    entityManager.clear();

    return savedBidHoldBalance;
  }
}