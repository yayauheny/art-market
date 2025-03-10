package by.yayauheny.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.OrderEntity;
import by.yayauheny.entity.PaymentEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.OrderStatus;
import by.yayauheny.enums.PaymentStatus;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.util.TestDataUtil;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PaymentEntityIT extends IntegrationBaseTest {

  @Test
  void save_validPayment_saved() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity buyer = TestDataUtil.getUser("john.the.buyer@example.com");
    var sellerWallet = TestDataUtil.getWallet(seller);
    var buyerWallet = TestDataUtil.getWallet(buyer);
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var order = TestDataUtil.getOrder(buyer, item, clock);
    var payment = TestDataUtil.getPayment(buyerWallet, order);

    session.persist(seller);
    session.persist(buyer);
    session.persist(sellerWallet);
    session.persist(buyerWallet);
    session.persist(category);
    session.persist(item);
    session.persist(order);
    session.persist(payment);
    session.flush();
    session.clear();
    var savedPayment = session.get(PaymentEntity.class, payment.getId());

    assertThat(savedPayment).isNotNull();
  }

  @Test
  void findById_paymentNotExist_notFound() {
    var paymentId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundPayment = session.get(PaymentEntity.class, paymentId);

    assertThat(foundPayment).isNull();
  }

  @Test
  void findById_multiplePayments_foundAll() {
    UserEntity firstSeller = TestDataUtil.getUser("john.doe@example.com");
    UserEntity secondSeller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity firstBuyer = TestDataUtil.getUser("john.thebuyer@example.com");
    UserEntity secondBuyer = TestDataUtil.getUser("john2.thebuyer@example.com");
    var firstSellerWallet = TestDataUtil.getWallet(firstSeller);
    var secondSellerWallet = TestDataUtil.getWallet(secondSeller);
    var firstBuyerWallet = TestDataUtil.getWallet(firstBuyer);
    var secondBuyerWallet = TestDataUtil.getWallet(secondBuyer);
    var firstCategory = TestDataUtil.getCategory("paintings");
    var secondCategory = TestDataUtil.getCategory("postcards");
    var firstItem = TestDataUtil.getItem("Still life Raphael", firstCategory, firstSeller);
    var secondItem = TestDataUtil.getItem("Postcard New Year", secondCategory, secondSeller);
    var firstOrder = TestDataUtil.getOrder(firstBuyer, firstItem, clock);
    var secondOrder = TestDataUtil.getOrder(secondBuyer, secondItem, clock);
    var firstPayment = TestDataUtil.getPayment(firstBuyerWallet, firstOrder);
    var secondPayment = TestDataUtil.getPayment(secondBuyerWallet, secondOrder);
    session.persist(firstSeller);
    session.persist(secondSeller);
    session.persist(firstBuyer);
    session.persist(secondBuyer);
    session.persist(firstSellerWallet);
    session.persist(secondSellerWallet);
    session.persist(firstBuyerWallet);
    session.persist(secondBuyerWallet);
    session.persist(firstCategory);
    session.persist(secondCategory);
    session.persist(firstItem);
    session.persist(secondItem);
    session.persist(firstOrder);
    session.persist(secondOrder);
    session.persist(firstPayment);
    session.persist(secondPayment);
    session.flush();
    session.clear();

    var firstSavedOrder = session.get(OrderEntity.class, firstOrder.getId());
    var secondSavedOrder = session.get(OrderEntity.class, secondOrder.getId());

    assertThat(List.of(firstSavedOrder, secondSavedOrder))
        .allMatch(Objects::nonNull);
  }

  @Test
  void remove_paymentExist_removed() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity buyer = TestDataUtil.getUser("john.the.buyer@example.com");
    var sellerWallet = TestDataUtil.getWallet(seller);
    var buyerWallet = TestDataUtil.getWallet(buyer);
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var order = TestDataUtil.getOrder(buyer, item, clock);
    var payment = TestDataUtil.getPayment(buyerWallet, order);
    session.persist(seller);
    session.persist(buyer);
    session.persist(sellerWallet);
    session.persist(buyerWallet);
    session.persist(category);
    session.persist(item);
    session.persist(order);
    session.persist(payment);
    session.flush();
    session.clear();
    var savedPayment = session.get(PaymentEntity.class, payment.getId());

    session.remove(savedPayment);
    session.flush();
    session.clear();
    var foundPayment = session.get(PaymentEntity.class, savedPayment.getId());

    assertThat(foundPayment).isNull();
  }

  @Test
  void update_updatedPaymentStatus_updated() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity buyer = TestDataUtil.getUser("john.the.buyer@example.com");
    var sellerWallet = TestDataUtil.getWallet(seller);
    var buyerWallet = TestDataUtil.getWallet(buyer);
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var order = TestDataUtil.getOrder(buyer, item, clock);
    var payment = TestDataUtil.getPayment(buyerWallet, order);
    PaymentStatus updatedStatus = PaymentStatus.FAILED;
    session.persist(seller);
    session.persist(buyer);
    session.persist(sellerWallet);
    session.persist(buyerWallet);
    session.persist(category);
    session.persist(item);
    session.persist(order);
    session.persist(payment);
    session.flush();
    session.clear();
    var savedPayment = session.get(PaymentEntity.class, payment.getId());

    savedPayment.setStatus(updatedStatus);
    session.merge(savedPayment);
    session.flush();
    session.clear();
    var updatedPayment = session.get(PaymentEntity.class, savedPayment.getId());

    assertThat(updatedPayment)
        .isNotNull()
        .isEqualTo(savedPayment);
  }
}