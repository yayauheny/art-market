package by.yayauheny.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "name")
@ToString(exclude = "items")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "category")
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true, length = 64)
  private String name;

  private String description;

  @Column(nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false, insertable = false)
  private Instant updatedAt;

  @OneToMany(mappedBy = "category")
  private List<ItemEntity> items;
}
