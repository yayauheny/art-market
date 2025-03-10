package by.yayauheny.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.OrderEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.OrderStatus;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.util.TestDataUtil;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderEntityIT extends IntegrationBaseTest {

  @Test
  void save_validOrder_saved() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity buyer = TestDataUtil.getUser("john.the.buyer@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var order = TestDataUtil.getOrder(buyer, item, clock);

    session.persist(seller);
    session.persist(buyer);
    session.persist(category);
    session.persist(item);
    session.persist(order);
    session.flush();
    session.clear();
    var savedOrder = session.get(OrderEntity.class, order.getId());

    assertThat(savedOrder).isNotNull();
  }

  @Test
  void findById_orderNotExist_notFound() {
    var orderId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundOrder = session.get(OrderEntity.class, orderId);

    assertThat(foundOrder).isNull();
  }

  @Test
  void findById_multipleOrders_foundAll() {
    UserEntity firstSeller = TestDataUtil.getUser("john.doe@example.com");
    UserEntity secondSeller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity firstBuyer = TestDataUtil.getUser("john.thebuyer@example.com");
    UserEntity secondBuyer = TestDataUtil.getUser("john2.thebuyer@example.com");
    var firstCategory = TestDataUtil.getCategory("paintings");
    var secondCategory = TestDataUtil.getCategory("postcards");
    var firstItem = TestDataUtil.getItem("Still life Raphael", firstCategory, firstSeller);
    var secondItem = TestDataUtil.getItem("Postcard New Year", secondCategory, secondSeller);
    var firstOrder = TestDataUtil.getOrder(firstBuyer, firstItem, clock);
    var secondOrder = TestDataUtil.getOrder(secondBuyer, secondItem, clock);
    session.persist(firstSeller);
    session.persist(secondSeller);
    session.persist(firstBuyer);
    session.persist(secondBuyer);
    session.persist(firstCategory);
    session.persist(secondCategory);
    session.persist(firstItem);
    session.persist(secondItem);
    session.persist(firstOrder);
    session.persist(secondOrder);
    session.flush();
    session.clear();

    var firstSavedOrder = session.get(OrderEntity.class, firstOrder.getId());
    var secondSavedOrder = session.get(OrderEntity.class, secondOrder.getId());

    assertThat(List.of(firstSavedOrder, secondSavedOrder))
        .allMatch(Objects::nonNull);
  }

  @Test
  void remove_orderExist_removed() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity buyer = TestDataUtil.getUser("john.the.buyer@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var order = TestDataUtil.getOrder(buyer, item, clock);
    session.persist(seller);
    session.persist(buyer);
    session.persist(category);
    session.persist(item);
    session.persist(order);
    session.flush();
    session.clear();
    var savedOrder = session.get(OrderEntity.class, order.getId());

    session.remove(savedOrder);
    session.flush();
    session.clear();
    var foundOrder = session.get(OrderEntity.class, savedOrder.getId());

    assertThat(foundOrder).isNull();
  }

  @Test
  void update_updatedOrderStatus_updated() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity buyer = TestDataUtil.getUser("john.the.buyer@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var order = TestDataUtil.getOrder(buyer, item, clock);
    session.persist(seller);
    session.persist(buyer);
    session.persist(category);
    session.persist(item);
    session.persist(order);
    session.flush();
    session.clear();
    var savedOrder = session.get(OrderEntity.class, order.getId());

    savedOrder.setStatus(OrderStatus.CANCELED);
    session.merge(savedOrder);
    session.flush();
    session.clear();
    var updatedOrder = session.get(OrderEntity.class, savedOrder.getId());

    assertThat(updatedOrder)
        .isNotNull()
        .isEqualTo(savedOrder);
  }
}