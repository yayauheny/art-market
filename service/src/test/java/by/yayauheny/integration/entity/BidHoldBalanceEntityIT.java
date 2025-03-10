package by.yayauheny.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.BidEntity;
import by.yayauheny.entity.BidHoldBalanceEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.BidHoldBalanceStatus;
import by.yayauheny.enums.BidStatus;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.util.TestDataUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BidHoldBalanceEntityIT extends IntegrationBaseTest {


  @Test
  void save_validBidHoldBalance_saved() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity bidOwner = TestDataUtil.getUser("rich.kid@example.com");
    var bidOwnerWallet = TestDataUtil.getWallet(bidOwner);
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    var bid = TestDataUtil.getBid(auction, bidOwner, BigDecimal.TEN);
    var bidHoldBalance = TestDataUtil.getBidHoldBalance(bidOwnerWallet, auction, bid);

    session.persist(seller);
    session.persist(bidOwner);
    session.persist(bidOwnerWallet);
    session.persist(category);
    session.persist(item);
    session.persist(auction);
    session.persist(bid);
    session.persist(bidHoldBalance);
    session.flush();
    session.clear();
    var savedBidHoldBalance = session.get(BidHoldBalanceEntity.class, bidHoldBalance.getId());

    assertThat(savedBidHoldBalance).isNotNull();
  }

  @Test
  void findById_bidHoldBalanceNotExist_notFound() {
    var bidHoldBalanceId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundBidHoldBalance = session.get(BidHoldBalanceEntity.class, bidHoldBalanceId);

    assertThat(foundBidHoldBalance).isNull();
  }

  @Test
  void findById_multipleBidHoldBalances_foundAll() {
    UserEntity firstSeller = TestDataUtil.getUser("john.doe@example.com");
    UserEntity secondSeller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity firstBidOwner = TestDataUtil.getUser("rich.kid@example.com");
    UserEntity secondBidOwner = TestDataUtil.getUser("rich2.kid@example.com");
    var firstBidOwnerWallet = TestDataUtil.getWallet(firstBidOwner);
    var secondBidOwnerWallet = TestDataUtil.getWallet(secondBidOwner);
    var firstCategory = TestDataUtil.getCategory("paintings");
    var secondCategory = TestDataUtil.getCategory("postcards");
    var firstItem = TestDataUtil.getItem("Still life Raphael", firstCategory, firstSeller);
    var secondItem = TestDataUtil.getItem("Postcard New Year", secondCategory, secondSeller);
    var firstAuction = TestDataUtil.getAuctionEntity(firstItem, clock);
    var secondAuction = TestDataUtil.getAuctionEntity(secondItem, clock);
    var firstBid = TestDataUtil.getBid(firstAuction, firstBidOwner, BigDecimal.TEN);
    var secondBid = TestDataUtil.getBid(secondAuction, secondBidOwner, BigDecimal.TEN);
    var firstBidHoldBalance = TestDataUtil.getBidHoldBalance(firstBidOwnerWallet, firstAuction,
        firstBid);
    var secondBidHoldBalance = TestDataUtil.getBidHoldBalance(secondBidOwnerWallet, secondAuction,
        secondBid);
    session.persist(firstSeller);
    session.persist(secondSeller);
    session.persist(firstBidOwner);
    session.persist(secondBidOwner);
    session.persist(firstBidOwnerWallet);
    session.persist(secondBidOwnerWallet);
    session.persist(firstCategory);
    session.persist(secondCategory);
    session.persist(firstItem);
    session.persist(secondItem);
    session.persist(firstAuction);
    session.persist(secondAuction);
    session.persist(firstBid);
    session.persist(secondBid);
    session.persist(firstBidHoldBalance);
    session.persist(secondBidHoldBalance);
    session.flush();
    session.clear();

    var firstSavedBidHoldBalance = session.get(BidHoldBalanceEntity.class,
        firstBidHoldBalance.getId());
    var secondSavedBidHoldBalance = session.get(BidHoldBalanceEntity.class,
        secondBidHoldBalance.getId());

    assertThat(List.of(firstSavedBidHoldBalance, secondSavedBidHoldBalance))
        .allMatch(Objects::nonNull);
  }

  @Test
  void remove_bidHoldBalanceExist_removed() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity bidOwner = TestDataUtil.getUser("rich.kid@example.com");
    var bidOwnerWallet = TestDataUtil.getWallet(bidOwner);
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    var bid = TestDataUtil.getBid(auction, bidOwner, BigDecimal.TEN);
    var bidHoldBalance = TestDataUtil.getBidHoldBalance(bidOwnerWallet, auction, bid);
    session.persist(seller);
    session.persist(bidOwner);
    session.persist(bidOwnerWallet);
    session.persist(category);
    session.persist(item);
    session.persist(auction);
    session.persist(bid);
    session.persist(bidHoldBalance);
    session.flush();
    session.clear();
    var savedBidHoldBalance = session.get(BidHoldBalanceEntity.class, bidHoldBalance.getId());

    session.remove(savedBidHoldBalance);
    session.flush();
    session.clear();
    var foundBidHoldBalance = session.get(BidHoldBalanceEntity.class, savedBidHoldBalance.getId());

    assertThat(foundBidHoldBalance).isNull();
  }

  @Test
  void update_updatedBidHoldBalanceStatus_updated() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity bidOwner = TestDataUtil.getUser("rich.kid@example.com");
    var bidOwnerWallet = TestDataUtil.getWallet(bidOwner);
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    var bid = TestDataUtil.getBid(auction, bidOwner, BigDecimal.TEN);
    var bidHoldBalance = TestDataUtil.getBidHoldBalance(bidOwnerWallet, auction, bid);
    session.persist(seller);
    session.persist(bidOwner);
    session.persist(bidOwnerWallet);
    session.persist(category);
    session.persist(item);
    session.persist(auction);
    session.persist(bid);
    session.persist(bidHoldBalance);
    session.flush();
    session.clear();
    var savedBidHoldBalance = session.get(BidHoldBalanceEntity.class, bidHoldBalance.getId());

    savedBidHoldBalance.setStatus(BidHoldBalanceStatus.CONFIRMED);
    session.merge(savedBidHoldBalance);
    session.flush();
    session.clear();
    var updatedBidHoldBalance = session.get(BidHoldBalanceEntity.class, bidHoldBalance.getId());

    assertThat(updatedBidHoldBalance)
        .isNotNull()
        .isEqualTo(savedBidHoldBalance);
  }
}