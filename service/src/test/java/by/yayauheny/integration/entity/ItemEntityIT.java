package by.yayauheny.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.ItemEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.ItemTransactionStatus;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.util.TestDataUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ItemEntityIT extends IntegrationBaseTest {

  @Test
  void save_validItem_saved() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);

    session.persist(seller);
    session.persist(category);
    session.persist(item);
    session.flush();
    session.clear();
    var savedItem = session.get(ItemEntity.class, item.getId());

    assertThat(savedItem).isNotNull();
  }

  @Test
  void findById_itemNotExist_notFound() {
    var itemId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundItem = session.get(ItemEntity.class, itemId);

    assertThat(foundItem).isNull();
  }

  @Test
  void findById_multipleItems_foundAll() {
    UserEntity firstSeller = TestDataUtil.getUser("john.doe@example.com");
    UserEntity secondSeller = TestDataUtil.getUser("john2.doe@example.com");
    var firstCategory = TestDataUtil.getCategory("paintings");
    var secondCategory = TestDataUtil.getCategory("postcards");
    var firstItem = TestDataUtil.getItem("Still life Raphael", firstCategory, firstSeller);
    var secondItem = TestDataUtil.getItem("Postcard New Year", secondCategory, secondSeller);
    session.persist(firstSeller);
    session.persist(secondSeller);
    session.persist(firstCategory);
    session.persist(secondCategory);
    session.persist(firstItem);
    session.persist(secondItem);
    session.flush();
    session.clear();

    var firstSavedItem = session.get(ItemEntity.class, firstItem.getId());
    var secondSavedItem = session.get(ItemEntity.class, secondItem.getId());

    assertThat(List.of(firstSavedItem, secondSavedItem))
        .allMatch(Objects::nonNull);
  }

  @Test
  void remove_itemExist_removed() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    session.persist(seller);
    session.persist(category);
    session.persist(item);
    session.flush();
    session.clear();
    var savedItem = session.get(ItemEntity.class, item.getId());

    session.remove(savedItem);
    session.flush();
    session.clear();
    var foundItem = session.get(ItemEntity.class, savedItem.getId());

    assertThat(foundItem).isNull();
  }

  @Test
  void update_updatedItemStatus_updated() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    session.persist(seller);
    session.persist(category);
    session.persist(item);
    session.flush();
    session.clear();
    var savedItem = session.get(ItemEntity.class, item.getId());

    savedItem.setStatus(ItemTransactionStatus.ACTIVE);
    session.merge(savedItem);
    session.flush();
    session.clear();
    var updatedItem = session.get(ItemEntity.class, savedItem.getId());

    assertThat(updatedItem)
        .isNotNull()
        .isEqualTo(savedItem);
  }
}