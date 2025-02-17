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
@Entity(name = "order")
public class OrderEntity {

  @Id
  private UUID id;
  private UUID userId;
  private UUID paymentId;
  private UUID itemId;
  private String status;  // Could be "pending", "paid", or "canceled"
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime expireAt;
}
