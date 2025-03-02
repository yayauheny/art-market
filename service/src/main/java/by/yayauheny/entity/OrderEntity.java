package by.yayauheny.entity;

import by.yayauheny.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"user", "item", "payments"})
@ToString(exclude = {"user", "item", "payments"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "item_id", nullable = false)
  private ItemEntity item;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false, insertable = false)
  private Instant updatedAt;

  @Column(nullable = false)
  private LocalDateTime expireAt;

  @OneToMany(mappedBy = "order")
  private List<PaymentEntity> payments;
}
