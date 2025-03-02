package by.yayauheny.entity;

import by.yayauheny.enums.ItemConditionType;
import by.yayauheny.enums.ItemTransactionStatus;
import by.yayauheny.enums.PaymentType;
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
import jakarta.persistence.Version;
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
@EqualsAndHashCode(exclude = {"category", "seller", "auctions", "order"})
@ToString(exclude = {"category", "seller", "auctions", "order"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "item")
public class ItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryEntity category;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "seller_id", nullable = false)
  private UserEntity seller;

  @Column(nullable = false)
  private String name;

  private String description;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private ItemConditionType condition;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private ItemTransactionStatus status;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(nullable = false, length = 3)
  private String currency;

  private String mediaPath;

  @Version
  @Column(nullable = false)
  private int version;

  @Column(nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false, insertable = false)
  private Instant updatedAt;

  @OneToMany(mappedBy = "item")
  private List<AuctionEntity> auctions;

  @OneToOne(mappedBy = "item")
  private OrderEntity order;
}
