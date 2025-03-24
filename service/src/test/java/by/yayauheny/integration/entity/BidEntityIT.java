package by.yayauheny.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.BidEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.BidStatus;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.util.TestDataUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BidEntityIT extends IntegrationBaseTest {

  @Test
  void save_validBid_saved() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity bidOwner = TestDataUtil.getUser("rich.kid@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    BigDecimal bidPrice = BigDecimal.TEN;
    var bid = TestDataUtil.getBid(auction, bidOwner, bidPrice);

    session.persist(seller);
    session.persist(bidOwner);
    session.persist(category);
    session.persist(item);
    session.persist(auction);
    session.persist(bid);
    session.flush();
    session.clear();
    var savedBid = session.get(BidEntity.class, bid.getId());

    assertThat(savedBid).isNotNull();
  }

  @Test
  void findById_bidNotExist_notFound() {
    var userId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundBid = session.get(BidEntity.class, userId);

    assertThat(foundBid).isNull();
  }

  @Test
  void findById_multipleBids_foundAll() {
    UserEntity firstSeller = TestDataUtil.getUser("john.doe@example.com");
    UserEntity secondSeller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity firstBidOwner = TestDataUtil.getUser("rich.kid@example.com");
    UserEntity secondBidOwner = TestDataUtil.getUser("rich2.kid@example.com");
    var firstCategory = TestDataUtil.getCategory("paintings");
    var secondCategory = TestDataUtil.getCategory("postcards");
    var firstItem = TestDataUtil.getItem("Still life Raphael", firstCategory, firstSeller);
    var secondItem = TestDataUtil.getItem("Postcard New Year", secondCategory, secondSeller);
    var firstAuction = TestDataUtil.getAuctionEntity(firstItem, clock);
    var secondAuction = TestDataUtil.getAuctionEntity(secondItem, clock);
    BigDecimal bidPrice = BigDecimal.TEN;
    var firstBid = TestDataUtil.getBid(firstAuction, firstBidOwner, bidPrice);
    var secondBid = TestDataUtil.getBid(secondAuction, secondBidOwner, bidPrice);
    session.persist(firstSeller);
    session.persist(secondSeller);
    session.persist(firstBidOwner);
    session.persist(secondBidOwner);
    session.persist(firstCategory);
    session.persist(secondCategory);
    session.persist(firstItem);
    session.persist(secondItem);
    session.persist(firstAuction);
    session.persist(secondAuction);
    session.persist(firstBid);
    session.persist(secondBid);
    session.flush();
    session.clear();

    var firstSavedBid = session.get(BidEntity.class, firstBid.getId());
    var secondSavedBid = session.get(BidEntity.class, secondBid.getId());

    assertThat(List.of(firstSavedBid, secondSavedBid))
        .allMatch(Objects::nonNull);
  }

  @Test
  void remove_bidExist_removed() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity bidOwner = TestDataUtil.getUser("rich.kid@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    BigDecimal bidPrice = BigDecimal.TEN;
    var bid = TestDataUtil.getBid(auction, bidOwner, bidPrice);
    session.persist(seller);
    session.persist(bidOwner);
    session.persist(category);
    session.persist(item);
    session.persist(auction);
    session.persist(bid);
    session.flush();
    session.clear();
    var savedBid = session.get(BidEntity.class, bid.getId());

    session.remove(savedBid);
    session.flush();
    session.clear();
    var foundBid = session.get(BidEntity.class, savedBid.getId());

    assertThat(foundBid).isNull();
  }

  @Test
  void update_updatedBidStatus_updated() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity bidOwner = TestDataUtil.getUser("rich.kid@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    BigDecimal bidPrice = BigDecimal.TEN;
    var bid = TestDataUtil.getBid(auction, bidOwner, bidPrice);
    session.persist(seller);
    session.persist(bidOwner);
    session.persist(category);
    session.persist(item);
    session.persist(auction);
    session.persist(bid);
    session.flush();
    session.clear();
    var savedBid = session.get(BidEntity.class, bid.getId());

    savedBid.setStatus(BidStatus.WON);
    session.merge(savedBid);
    session.flush();
    session.clear();
    var updatedAuction = session.get(BidEntity.class, savedBid.getId());

    assertThat(updatedAuction)
        .isNotNull()
        .isEqualTo(savedBid);
  }
}