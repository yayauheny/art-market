package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.AuctionEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.repository.AuctionRepository;
import by.yayauheny.repository.CategoryRepository;
import by.yayauheny.repository.ItemRepository;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.util.IocIntegrationTest;
import by.yayauheny.util.TestDataUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(IocIntegrationTest.class)
public class AuctionRepositoryIT extends IntegrationBaseTest {

  private UserRepository userRepository;
  private ItemRepository itemRepository;
  private CategoryRepository categoryRepository;
  private AuctionRepository auctionRepository;

  @BeforeEach
  void initDependencies() {
    userRepository = new UserRepository(session);
    itemRepository = new ItemRepository(session);
    categoryRepository = new CategoryRepository(session);
    categoryRepository = new CategoryRepository(session);
    auctionRepository = new AuctionRepository(session);
  }

  @Test
  void save_validAuction_saved() {
    var auction = createAndSaveAuction();

    var savedAuction = auctionRepository.findById(auction.getId());
    assertThat(savedAuction.get().getId()).isEqualTo(auction.getId());
  }

  @Test
  void findById_auctionNotExist_notFound() {
    var auctionId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundAuction = auctionRepository.findById(auctionId);

    assertThat(foundAuction).isEmpty();
  }

  @Test
  void findById_auctionExist_found() {
    var auction = createAndSaveAuction();

    var savedAuction = auctionRepository.findById(auction.getId()).get();

    assertThat(savedAuction.getId()).isEqualTo(auction.getId());
  }

  @Test
  void delete_auctionExist_deleted() {
    var auction = createAndSaveAuction();
    var savedAuction = auctionRepository.findById(auction.getId()).get();

    auctionRepository.delete(savedAuction);

    session.clear();
    var foundAuction = auctionRepository.findById(savedAuction.getId());
    assertThat(foundAuction).isEmpty();
  }

  @Test
  void update_validAuction_updated() {
    var auction = createAndSaveAuction();
    var savedAuction = auctionRepository.findById(auction.getId()).get();
    BigDecimal updatedInitialBidPrice = BigDecimal.ZERO.setScale(2, RoundingMode.CEILING);
    savedAuction.setInitialBidPrice(updatedInitialBidPrice);

    auctionRepository.update(savedAuction);

    session.flush();
    session.clear();
    var updatedAuction = auctionRepository.findById(savedAuction.getId()).get();
    assertThat(updatedAuction.getInitialBidPrice()).isEqualTo(updatedInitialBidPrice);
  }

  private AuctionEntity createAndSaveAuction() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var auction = TestDataUtil.getAuctionEntity(item, clock);
    userRepository.save(seller);
    categoryRepository.save(category);
    itemRepository.save(item);
    var savedAuction = auctionRepository.save(auction);
    session.flush();
    session.clear();

    return savedAuction;
  }
}
