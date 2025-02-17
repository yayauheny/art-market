package by.yayauheny.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "wallet")
public class WalletEntity {

  @Id
  private UUID id;
  private UUID ownerId;
  private String currency;
  private BigDecimal balance;
  private String paymentInfo;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
