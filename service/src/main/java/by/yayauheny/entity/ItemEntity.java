package by.yayauheny.entity;

import by.yayauheny.enums.ItemConditionType;
import by.yayauheny.enums.ItemTransactionStatus;
import by.yayauheny.enums.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
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
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, exclude = {"category", "seller", "auctions", "order"})
@ToString(callSuper = true, exclude = {"category", "seller", "auctions", "order"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "item")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class ItemEntity extends BaseEntity {

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

  @OneToMany(mappedBy = "item")
  @Builder.Default
  private List<AuctionEntity> auctions = new ArrayList<>();

  @OneToOne(mappedBy = "item")
  private OrderEntity order;

  public void setOrder(OrderEntity order) {
    this.order = order;
    order.setItem(this);
  }

  public void addAuction(AuctionEntity auction) {
    auctions.add(auction);
    auction.setItem(this);
  }
}
