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
public class ItemFilter {

  String categoryName;
  UUID sellerId;
  String name;
  String description;
  ItemConditionType condition;
  PaymentType paymentType;
  ItemTransactionStatus status;
  Filter<BigDecimal> price;
}
