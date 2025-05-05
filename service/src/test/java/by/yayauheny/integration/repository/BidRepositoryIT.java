package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.BidEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.BidStatus;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.integration.annotation.IT;
import by.yayauheny.repository.AuctionRepository;
import by.yayauheny.repository.BidRepository;
import by.yayauheny.repository.CategoryRepository;
import by.yayauheny.repository.ItemRepository;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.util.TestDataUtil;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
class BidRepositoryIT extends IntegrationBaseTest {

  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final CategoryRepository categoryRepository;
  private final AuctionRepository auctionRepository;
  private final BidRepository bidRepository;

  @Test
  void save_validBid_saved() {
    var bid = createAndSaveBid();

    var savedBid = bidRepository.findById(bid.getId()).get();
    assertThat(savedBid.getId()).isEqualTo(bid.getId());
  }

  @Test
  void findById_bidNotExist_notFound() {
    var bidId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundBid = bidRepository.findById(bidId);

    assertThat(foundBid).isEmpty();
  }

  @Test
  void findById_bidExist_found() {
    var bid = createAndSaveBid();

    var savedBid = bidRepository.findById(bid.getId()).get();

    assertThat(savedBid.getId()).isEqualTo(bid.getId());
  }

  @Test
  void delete_bidExist_deleted() {
    var bid = createAndSaveBid();
    var savedBid = bidRepository.findById(bid.getId()).get();

    bidRepository.delete(savedBid);

    entityManager.flush();
    entityManager.clear();
    var foundBid = bidRepository.findById(savedBid.getId());
    assertThat(foundBid).isEmpty();
  }

  @Test
  void update_updatedBidStatus_updated() {
    var bid = createAndSaveBid();
    var savedBid = bidRepository.findById(bid.getId()).get();
    savedBid.setStatus(BidStatus.WON);

    bidRepository.save(savedBid);

    entityManager.flush();
    entityManager.clear();
    var updatedAuction = bidRepository.findById(bid.getId()).get();
    assertThat(updatedAuction.getId()).isEqualTo(bid.getId());
  }

  private BidEntity createAndSaveBid() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity bidOwner = TestDataUtil.getUser("rich.kid@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    BigDecimal bidPrice = BigDecimal.TEN;
    var bid = TestDataUtil.getBid(auction, bidOwner, bidPrice);
    userRepository.save(seller);
    userRepository.save(bidOwner);
    categoryRepository.save(category);
    itemRepository.save(item);
    auctionRepository.save(auction);
    var savedBid = bidRepository.save(bid);
    entityManager.flush();
    entityManager.clear();

    return savedBid;
  }
}