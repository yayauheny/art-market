package by.yayauheny.entity;

import by.yayauheny.enums.AuctionStatus;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(exclude = {"item", "bids", "heldBalances"})
@ToString(exclude = {"item", "bids", "heldBalances"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "auction")
public class AuctionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

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

  @Column(nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false, insertable = false)
  private Instant updatedAt;

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
