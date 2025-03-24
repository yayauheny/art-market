package by.yayauheny.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.AuctionEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.util.TestDataUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AuctionEntityIT extends IntegrationBaseTest {

  @Test
  void save_validAuction_saved() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);

    session.persist(seller);
    session.persist(category);
    session.persist(item);
    session.persist(auction);
    session.flush();
    session.clear();
    var savedAuction = session.get(AuctionEntity.class, auction.getId());

    assertThat(savedAuction).isNotNull();
  }

  @Test
  void findById_auctionNotExist_notFound() {
    var userId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundAuction = session.get(AuctionEntity.class, userId);

    assertThat(foundAuction).isNull();
  }

  @Test
  void findById_multipleAuctions_foundAll() {
    UserEntity firstSeller = TestDataUtil.getUser("john.doe@example.com");
    UserEntity secondSeller = TestDataUtil.getUser("john2.doe@example.com");
    var firstCategory = TestDataUtil.getCategory("paintings");
    var secondCategory = TestDataUtil.getCategory("postcards");
    var firstItem = TestDataUtil.getItem("Still life Raphael", firstCategory, firstSeller);
    var secondItem = TestDataUtil.getItem("Postcard New Year", secondCategory, secondSeller);
    var firstAuction = TestDataUtil.getAuctionEntity(firstItem, clock);
    var secondAuction = TestDataUtil.getAuctionEntity(secondItem, clock);
    session.persist(firstSeller);
    session.persist(secondSeller);
    session.persist(firstCategory);
    session.persist(secondCategory);
    session.persist(firstItem);
    session.persist(secondItem);
    session.persist(firstAuction);
    session.persist(secondAuction);
    session.flush();
    session.clear();

    var firstSavedAuction = session.get(AuctionEntity.class, firstAuction.getId());
    var secondSavedAuction = session.get(AuctionEntity.class, secondAuction.getId());

    assertThat(List.of(firstSavedAuction, secondSavedAuction))
        .allMatch(Objects::nonNull);
  }

  @Test
  void remove_auctionExist_removed() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    session.persist(seller);
    session.persist(category);
    session.persist(item);
    session.persist(auction);
    session.flush();
    session.clear();
    var savedAuction = session.get(AuctionEntity.class, auction.getId());

    session.remove(savedAuction);
    session.flush();
    session.clear();
    var foundAuction = session.get(AuctionEntity.class, savedAuction.getId());

    assertThat(foundAuction).isNull();
  }

  @Test
  void update_validAuction_updated() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    session.persist(seller);
    session.persist(category);
    session.persist(item);
    session.persist(auction);
    session.flush();
    session.clear();
    var savedAuction = session.get(AuctionEntity.class, auction.getId());

    savedAuction.setInitialBidPrice(BigDecimal.ZERO.setScale(2, RoundingMode.CEILING));
    session.merge(savedAuction);
    session.flush();
    session.clear();
    var updatedAuction = session.get(AuctionEntity.class, savedAuction.getId());

    assertThat(updatedAuction)
        .isNotNull()
        .isEqualTo(savedAuction);
  }
}