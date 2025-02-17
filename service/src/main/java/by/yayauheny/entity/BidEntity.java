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
@Entity(name = "bid")
public class BidEntity {

  @Id
  private UUID id;
  private UUID auctionId;
  private UUID userId;
  private String status;
  private BigDecimal price;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
