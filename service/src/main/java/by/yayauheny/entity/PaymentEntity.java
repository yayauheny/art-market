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
@Entity(name = "payment")
public class PaymentEntity {

  @Id
  private UUID id;
  private UUID walletId;
  private BigDecimal amount;
  private String currency;
  private String type;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
