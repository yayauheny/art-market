package by.yayauheny.entity;

import by.yayauheny.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, exclude = {"user", "item", "payments"})
@ToString(callSuper = true, exclude = {"user", "item", "payments"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class OrderEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "item_id", nullable = false)
  private ItemEntity item;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(nullable = false)
  private Instant expireAt;

  @OneToMany(mappedBy = "order")
  @Builder.Default
  private List<PaymentEntity> payments = new ArrayList<>();

  public void addPayment(PaymentEntity payment) {
    payments.add(payment);
    payment.setOrder(this);
  }
}
