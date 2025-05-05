package by.yayauheny.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
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
@EqualsAndHashCode(callSuper = true, exclude = {"owner", "payments", "bidHoldBalances"})
@ToString(callSuper = true, exclude = {"owner", "payments", "bidHoldBalances"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "wallet")
public class WalletEntity extends BaseEntity {

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "owner_id", nullable = false)
  private UserEntity owner;

  @Column(nullable = false, length = 3)
  private String currency;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal balance;

  @Column(nullable = false)
  private String paymentInfo;

  @OneToMany(mappedBy = "wallet")
  @Builder.Default
  private List<PaymentEntity> payments = new ArrayList<>();

  @OneToMany(mappedBy = "wallet")
  @Builder.Default
  private List<BidHoldBalanceEntity> bidHoldBalances = new ArrayList<>();

  public void addPayment(PaymentEntity payment) {
    payments.add(payment);
    payment.setWallet(this);
  }

  public void addBidHoldBalance(BidHoldBalanceEntity bidHoldBalance) {
    bidHoldBalances.add(bidHoldBalance);
    bidHoldBalance.setWallet(this);
  }
}
