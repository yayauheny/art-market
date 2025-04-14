package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.OrderEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.enums.OrderStatus;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.integration.annotation.IT;
import by.yayauheny.repository.CategoryRepository;
import by.yayauheny.repository.ItemRepository;
import by.yayauheny.repository.OrderRepository;
import by.yayauheny.repository.UserRepository;
import by.yayauheny.util.TestDataUtil;
import java.time.Clock;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@IT
@RequiredArgsConstructor
class OrderRepositoryIT extends IntegrationBaseTest {

  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final CategoryRepository categoryRepository;
  private final OrderRepository orderRepository;

  @Test
  void save_validOrder_saved() {
    var order = createAndSaveOrder(clock);

    var savedOrder = orderRepository.findById(order.getId()).get();
    assertThat(savedOrder.getId()).isEqualTo(order.getId());
  }

  @Test
  void findById_orderNotExist_notFound() {
    var orderId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundOrder = orderRepository.findById(orderId);

    assertThat(foundOrder).isEmpty();
  }

  @Test
  void findById_orderExist_found() {
    var order = createAndSaveOrder(clock);

    var savedOrder = orderRepository.findById(order.getId());

    assertThat(savedOrder).isPresent();
  }

  @Test
  void delete_orderExist_deleted() {
    var order = createAndSaveOrder(clock);
    var savedOrder = orderRepository.findById(order.getId()).get();

    orderRepository.delete(savedOrder);

    entityManager.flush();
    var foundOrder = orderRepository.findById(savedOrder.getId());
    assertThat(foundOrder).isEmpty();
  }

  @Test
  void update_updatedOrderStatus_updated() {
    var order = createAndSaveOrder(clock);
    var savedOrder = orderRepository.findById(order.getId()).get();
    OrderStatus updatedStatus = OrderStatus.CANCELED;
    savedOrder.setStatus(updatedStatus);

    orderRepository.save(savedOrder);

    entityManager.flush();
    entityManager.clear();
    var updatedOrder = orderRepository.findById(savedOrder.getId()).get();
    assertThat(updatedOrder.getStatus()).isEqualTo(updatedStatus);
  }

  private OrderEntity createAndSaveOrder(Clock clock) {
    UserEntity seller = TestDataUtil.getUser("john2.doe@example.com");
    UserEntity buyer = TestDataUtil.getUser("john.the.buyer@example.com");
    var category = TestDataUtil.getCategory("paintings");
    var item = TestDataUtil.getItem("Still life Raphael", category, seller);
    var order = TestDataUtil.getOrder(buyer, item, clock);
    userRepository.save(seller);
    userRepository.save(buyer);
    categoryRepository.save(category);
    itemRepository.save(item);
    var savedOrder = orderRepository.save(order);
    entityManager.flush();
    entityManager.clear();

    return savedOrder;
  }
}