package by.yayauheny.entity;

import by.yayauheny.enums.BidStatus;
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
@EqualsAndHashCode(exclude = {"auction", "user"})
@ToString(exclude = {"auction", "user"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bid")
public class BidEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "auction_id", nullable = false)
  private AuctionEntity auction;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private BidStatus status;

  @Column(nullable = false, precision = 8, scale = 2)
  private BigDecimal price;

  @Column(nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false, insertable = false)
  private Instant updatedAt;
}
