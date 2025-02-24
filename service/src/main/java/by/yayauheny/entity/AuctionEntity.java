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
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"item", "bids"})
@ToString(exclude = {"item", "bids"})
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

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AuctionStatus status;

  @Column(nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false, insertable = false)
  private Instant updatedAt;

  private Instant finishedAt;

  @OneToMany(mappedBy = "auction")
  private List<BidEntity> bids;

  @OneToMany(mappedBy = "auction")
  private List<BidHoldBalanceEntity> heldBalances;
}
