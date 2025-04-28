package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.dto.filter.Filter;
import by.yayauheny.dto.filter.ItemFilter;
import by.yayauheny.entity.CategoryEntity;
import by.yayauheny.entity.ItemEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.ItemTransactionStatus;
import by.yayauheny.enums.OperatorCompareType;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.integration.annotation.IT;
import by.yayauheny.repository.CategoryRepository;
import by.yayauheny.repository.ItemRepository;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.util.TestDataUtil;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@IT
@RequiredArgsConstructor
class ItemRepositoryIT extends IntegrationBaseTest {

  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final CategoryRepository categoryRepository;

  @Test
  void save_validItem_saved() {
    var item = createAndSaveItem();

    var savedItem = itemRepository.findById(item.getId()).get();
    assertThat(savedItem.getId()).isEqualTo(item.getId());
  }

  @Test
  void findById_itemNotExist_notFound() {
    var itemId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundItem = itemRepository.findById(itemId);

    assertThat(foundItem).isEmpty();
  }

  @Test
  void findById_itemExist_found() {
    var item = createAndSaveItem();

    var savedItem = itemRepository.findById(item.getId()).get();

    assertThat(savedItem.getId()).isEqualTo(item.getId());
  }

  @Test
  void remove_itemExist_removed() {
    var item = createAndSaveItem();
    var savedItem = itemRepository.findById(item.getId()).get();

    itemRepository.delete(savedItem);

    entityManager.clear();
    var foundItem = itemRepository.findById(savedItem.getId());
    assertThat(foundItem).isEmpty();
  }

  @Test
  void update_updatedItemStatus_updated() {
    var item = createAndSaveItem();
    var savedItem = itemRepository.findById(item.getId()).get();
    ItemTransactionStatus updatedStatus = ItemTransactionStatus.ACTIVE;
    savedItem.setStatus(updatedStatus);

    itemRepository.update(savedItem);

    entityManager.flush();
    entityManager.clear();
    var updatedItem = itemRepository.findById(savedItem.getId()).get();
    assertThat(updatedItem.getStatus()).isEqualTo(updatedStatus);
  }

  @Test
  void findItemsByFilter_withNameContainingAndPriceGOETo300_returnMatchingItems() {
    var firstItem = createAndSaveItem("Blue Room, Pablo Picasso", BigDecimal.valueOf(59999.99));
    var secondItem = createAndSaveItem("Life, Pablo Picasso", BigDecimal.valueOf(299.99));
    var thirdItem = createAndSaveItem("Dream, Pablo Picasso", BigDecimal.valueOf(999999.99));
    itemRepository.save(firstItem);
    itemRepository.save(secondItem);
    itemRepository.save(thirdItem);
    entityManager.flush();
    entityManager.clear();
    var authorName = "Pablo Picasso";
    ItemFilter filter = ItemFilter.builder()
        .name(authorName)
        .price(new Filter<>(BigDecimal.valueOf(300), OperatorCompareType.GOE))
        .build();

    List<ItemEntity> foundItems = itemRepository.findItemsByFilter(filter);

    assertThat(foundItems)
        .extracting(ItemEntity::getName)
        .containsExactlyInAnyOrder(
            firstItem.getName(),
            thirdItem.getName()
        );
  }

  @Test
  void findItemsByFilter_withCategoryNameAndSellerId_returnMatchingItems() {
    var seller = TestDataUtil.getUser("johntheseller.doe@example.com");
    var firstItem = createAndSaveItem(
        "Abstract Art",
        BigDecimal.valueOf(500.00),
        "Art",
        seller
    );
    var secondItem = createAndSaveItem(
        "Modern Sculpture",
        BigDecimal.valueOf(1500.00),
        "Sculpture",
        seller
    );
    var thirdItem = createAndSaveItem(
        "Vintage Vase",
        BigDecimal.valueOf(300.00),
        "Antiques",
        seller
    );
    itemRepository.save(firstItem);
    itemRepository.save(secondItem);
    itemRepository.save(thirdItem);
    entityManager.flush();
    entityManager.clear();
    ItemFilter filter = ItemFilter.builder()
        .categoryName("Art")
        .sellerId(seller.getId())
        .build();

    List<ItemEntity> foundItems = itemRepository.findItemsByFilter(filter);

    assertThat(foundItems)
        .extracting(ItemEntity::getName)
        .containsExactly(firstItem.getName());
  }

  @Test
  void countSoldItemsBetweenDates_withinDateRange_returnsCorrectCount() {
    Instant now = Instant.now();
    Instant oneDayAgo = now.minus(1, ChronoUnit.DAYS);
    Instant twoDaysAgo = now.minus(2, ChronoUnit.DAYS);
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var category = TestDataUtil.getCategory("paintings");
    UserEntity savedSeller = userRepository.save(seller);
    CategoryEntity savedCategory = categoryRepository.save(category);
    entityManager.flush();
    ItemEntity firstSoldItem = createAndSaveItem(
        "Item",
        BigDecimal.valueOf(100.00),
        oneDayAgo,
        ItemTransactionStatus.SOLD,
        savedSeller,
        savedCategory
    );
    ItemEntity secondSoldItem = createAndSaveItem(
        "Item",
        BigDecimal.valueOf(200.00),
        twoDaysAgo,
        ItemTransactionStatus.SOLD,
        savedSeller,
        savedCategory
    );
    ItemEntity unsoldItem = createAndSaveItem(
        "Item",
        BigDecimal.valueOf(300.00),
        now,
        ItemTransactionStatus.RESERVED,
        savedSeller,
        savedCategory
    );
    itemRepository.save(firstSoldItem);
    itemRepository.save(secondSoldItem);
    itemRepository.save(unsoldItem);
    entityManager.flush();
    entityManager.clear();

    long count = itemRepository.countSoldItemsBetweenDates(oneDayAgo, now);

    assertThat(count).isEqualTo(2);
  }

  private ItemEntity createAndSaveItem() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    userRepository.save(seller);
    categoryRepository.save(category);
    ItemEntity savedItem = itemRepository.save(item);
    entityManager.flush();
    entityManager.clear();

    return savedItem;
  }

  private ItemEntity createAndSaveItem(String name, BigDecimal price) {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var categoryName = "paintings";
    return createAndSaveItem(name, price, categoryName, seller);
  }

  private ItemEntity createAndSaveItem(
      String name,
      BigDecimal price,
      String categoryName,
      UserEntity seller
  ) {
    var category = TestDataUtil.getCategory(categoryName);
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    ItemEntity buildItem = item.toBuilder()
        .name(name)
        .price(price)
        .build();
    userRepository.save(seller);
    categoryRepository.save(category);
    ItemEntity savedItem = itemRepository.save(buildItem);
    entityManager.flush();
    entityManager.clear();

    return savedItem;
  }

  private ItemEntity createAndSaveItem(
      String name,
      BigDecimal price,
      Instant updatedAt,
      ItemTransactionStatus status,
      UserEntity seller,
      CategoryEntity category
  ) {
    var item = TestDataUtil.getItem(name, category, seller);
    ItemEntity buildItem = item.toBuilder()
        .name(name)
        .price(price)
        .updatedAt(updatedAt)
        .status(status)
        .build();
    ItemEntity savedItem = itemRepository.save(buildItem);
    entityManager.flush();
    entityManager.clear();

    return savedItem;
  }
}