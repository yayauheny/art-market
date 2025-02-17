package by.yayauheny.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "item")
public class ItemEntity {

  @Id
  private UUID id;
  private UUID categoryId;
  private UUID sellerId;
  private String purchaseType;
  private String name;
  private String description;
  private String condition;
  private String status;
  private BigDecimal price;
  private String currency;
  private String imagePath;
  private short version;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
