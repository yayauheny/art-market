package by.yayauheny.dto;

import by.yayauheny.entity.BidHoldBalanceEntity;
import by.yayauheny.entity.PaymentEntity;
import by.yayauheny.entity.UserEntity;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record WalletResponse(
    UUID id,
    UserEntity owner,
    String currency,
    BigDecimal balance,
    String paymentInfo,
    Instant createdAt,
    Instant updatedAt,
    List<PaymentEntity> payments,
    List<BidHoldBalanceEntity> bidHoldBalances
) {

}
