package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.PaymentEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.PaymentStatus;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.repository.CategoryRepository;
import by.yayauheny.repository.ItemRepository;
import by.yayauheny.repository.OrderRepository;
import by.yayauheny.repository.PaymentRepository;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.repository.WalletRepository;
import by.yayauheny.util.IocIntegrationTest;
import by.yayauheny.util.TestDataUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(IocIntegrationTest.class)
@RequiredArgsConstructor
class PaymentRepositoryIT extends IntegrationBaseTest {

  private final UserRepository userRepository;
  private final WalletRepository walletRepository;
  private final ItemRepository itemRepository;
  private final CategoryRepository categoryRepository;
  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;

  @Test
  void save_validPayment_saved() {
    var payment = createAndSavePayment();

    var savedPayment = paymentRepository.findById(payment.getId()).get();
    assertThat(savedPayment.getId()).isEqualTo(payment.getId());
  }

  @Test
  void findById_paymentNotExist_notFound() {
    var paymentId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundPayment = paymentRepository.findById(paymentId);

    assertThat(foundPayment).isEmpty();
  }

  @Test
  void findById_paymentExist_found() {
    var payment = createAndSavePayment();

    var savedOrder = paymentRepository.findById(payment.getId()).get();

    assertThat(savedOrder.getId()).isEqualTo(payment.getId());
  }

  @Test
  void delete_paymentExist_deleted() {
    var payment = createAndSavePayment();
    var savedPayment = paymentRepository.findById(payment.getId()).get();

    paymentRepository.delete(savedPayment);

    session.clear();
    var foundPayment = paymentRepository.findById(savedPayment.getId());
    assertThat(foundPayment).isEmpty();
  }

  @Test
  void update_updatedPaymentStatus_updated() {
    var payment = createAndSavePayment();
    var savedPayment = paymentRepository.findById(payment.getId()).get();
    PaymentStatus updatedStatus = PaymentStatus.FAILED;
    savedPayment.setStatus(updatedStatus);

    paymentRepository.update(savedPayment);

    session.flush();
    session.clear();
    var updatedPayment = paymentRepository.findById(savedPayment.getId()).get();
    assertThat(updatedPayment.getStatus()).isEqualTo(updatedStatus);
  }

  private PaymentEntity createAndSavePayment() {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity buyer = TestDataUtil.getUser("john.the.buyer@example.com");
    var sellerWallet = TestDataUtil.getWallet(seller);
    var buyerWallet = TestDataUtil.getWallet(buyer);
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var order = TestDataUtil.getOrder(buyer, item, clock);
    var payment = TestDataUtil.getPayment(buyerWallet, order);
    userRepository.save(seller);
    userRepository.save(buyer);
    walletRepository.save(sellerWallet);
    walletRepository.save(buyerWallet);
    categoryRepository.save(category);
    itemRepository.save(item);
    orderRepository.save(order);
    var savedPayment = paymentRepository.save(payment);
    session.flush();
    session.clear();

    return savedPayment;
  }
}