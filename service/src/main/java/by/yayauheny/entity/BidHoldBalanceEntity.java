package by.yayauheny.entity;

import by.yayauheny.enums.BidHoldBalanceStatus;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"wallet", "auction", "bid"})
@ToString(exclude = {"wallet", "auction", "bid"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bid_hold_balance")
public class BidHoldBalanceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wallet_id")
  private WalletEntity wallet;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "auction_id")
  private AuctionEntity auction;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "bid_id")
  private BidEntity bid;

  @Column(nullable = false, precision = 8, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private String currency;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private BidHoldBalanceStatus status;

  @Column(nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false, insertable = false)
  private Instant updatedAt;
}
