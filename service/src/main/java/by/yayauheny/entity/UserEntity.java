package by.yayauheny.entity;

import by.yayauheny.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "email")
@ToString(exclude = {"wallet", "itemsForSale"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

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

  @Column(nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false, insertable = false)
  private Instant updatedAt;

  @OneToOne(mappedBy = "owner", optional = false)
  private WalletEntity wallet;

  @OneToMany(mappedBy = "seller")
  private List<ItemEntity> itemsForSale;

  @OneToMany(mappedBy = "user")
  private List<BidEntity> bids;

  @OneToMany(mappedBy = "user")
  private List<OrderEntity> orders;
}
