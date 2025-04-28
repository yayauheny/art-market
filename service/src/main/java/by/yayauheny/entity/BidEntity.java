package by.yayauheny.entity;

import by.yayauheny.enums.BidStatus;
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
@EqualsAndHashCode(callSuper = true, exclude = {"auction", "user"})
@ToString(callSuper = true, exclude = {"auction", "user"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bid")
public class BidEntity extends BaseEntity {

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
}
