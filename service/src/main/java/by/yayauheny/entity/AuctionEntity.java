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
@Entity(name = "auction")
public class AuctionEntity {

  @Id
  private UUID id;
  private UUID sellerId;
  private UUID itemId;
  private UUID bidId;
  private BigDecimal startingPrice;
  private BigDecimal minStep;
  private String status;
  private long duration;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime finishedAt;
}
