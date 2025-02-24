package by.yayauheny.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"owner", "wallet"})
@ToString(exclude = {"owner", "wallet"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "wallet")
public class WalletEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "owner_id", nullable = false)
  private UserEntity owner;

  @Column(nullable = false, length = 3)
  private String currency;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal balance;

  @Column(nullable = false)
  private String paymentInfo;

  @Column(nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false, insertable = false)
  private Instant updatedAt;

  @OneToMany(mappedBy = "wallet")
  private List<PaymentEntity> payments;

  @OneToMany(mappedBy = "wallet")
  private List<BidHoldBalanceEntity> bidHolds;
}
