package by.yayauheny.entity;

import by.yayauheny.enums.BidHoldBalanceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, exclude = {"wallet", "auction", "bid"})
@ToString(callSuper = true, exclude = {"wallet", "auction", "bid"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bid_hold_balance")
public class BidHoldBalanceEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wallet_id", nullable = false)
  private WalletEntity wallet;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "auction_id", nullable = false)
  private AuctionEntity auction;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "bid_id", nullable = false)
  private BidEntity bid;

  @Column(nullable = false, precision = 8, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private String currency;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private BidHoldBalanceStatus status;
}
