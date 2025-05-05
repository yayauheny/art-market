package by.yayauheny.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@EqualsAndHashCode(callSuper = true, of = "name")
@ToString(callSuper = true, of = "name")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "category")
public class CategoryEntity extends BaseEntity {

  @Column(nullable = false, unique = true, length = 64)
  private String name;

  private String description;

  @OneToMany(mappedBy = "category")
  @Builder.Default
  private List<ItemEntity> items = new ArrayList<>();

  public void addItem(ItemEntity item) {
    items.add(item);
    item.setCategory(this);
  }
}
