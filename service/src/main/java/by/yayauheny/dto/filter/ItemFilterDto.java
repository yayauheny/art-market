package by.yayauheny.dto.filter;

import by.yayauheny.enums.ItemConditionType;
import by.yayauheny.enums.ItemTransactionStatus;
import by.yayauheny.enums.PaymentType;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemFilterDto {

  private String categoryName;
  private UUID sellerId;
  private String name;
  private String description;
  private ItemConditionType condition;
  private PaymentType paymentType;
  private ItemTransactionStatus status;
  private Filter<BigDecimal> price;
}
