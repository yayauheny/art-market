package by.yayauheny.entity;

import by.yayauheny.enums.AuctionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
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
@EqualsAndHashCode(callSuper = true, exclude = {"item", "bids", "heldBalances"})
@ToString(callSuper = true, exclude = {"item", "bids", "heldBalances"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "auction")
public class AuctionEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "item_id", nullable = false)
  private ItemEntity item;

  @Column(nullable = false, precision = 8, scale = 2)
  private BigDecimal initialBidPrice;

  @Column(nullable = false, precision = 8, scale = 2)
  private BigDecimal minBidStep;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private AuctionStatus status;

  private Instant finishedAt;

  @OneToMany(mappedBy = "auction")
  @Builder.Default
  private List<BidEntity> bids = new ArrayList<>();

  @OneToMany(mappedBy = "auction")
  @Builder.Default
  private List<BidHoldBalanceEntity> heldBalances = new ArrayList<>();

  public void addBid(BidEntity bid) {
    bids.add(bid);
    bid.setAuction(this);
  }
}
