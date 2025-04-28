package by.yayauheny.entity;

import by.yayauheny.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
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
@EqualsAndHashCode(callSuper = true, of = "email")
@ToString(callSuper = true, exclude = {"wallet", "itemsForSale", "bids", "orders"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity extends BaseEntity {

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  private String lastName;

  @Column(nullable = false, length = 32)
  @Enumerated(EnumType.STRING)
  private Role role;

  private String address;

  @Column(nullable = false)
  private LocalDate birthDate;

  @OneToOne(mappedBy = "owner")
  private WalletEntity wallet;

  @OneToMany(mappedBy = "seller")
  @Builder.Default
  private List<ItemEntity> itemsForSale = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  @Builder.Default
  private List<BidEntity> bids = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  @Builder.Default
  private List<OrderEntity> orders = new ArrayList<>();

  public void setWallet(WalletEntity wallet) {
    this.wallet = wallet;
    wallet.setOwner(this);
  }

  public void addItemForSale(ItemEntity item) {
    itemsForSale.add(item);
    item.setSeller(this);
  }

  public void addBid(BidEntity bid) {
    bids.add(bid);
    bid.setUser(this);
  }

  public void addOrder(OrderEntity order) {
    orders.add(order);
    order.setUser(this);
  }
}
