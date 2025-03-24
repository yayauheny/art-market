package by.yayauheny.util;

import by.yayauheny.entity.AuctionEntity;
import by.yayauheny.entity.BidEntity;
import by.yayauheny.entity.BidHoldBalanceEntity;
import by.yayauheny.entity.CategoryEntity;
import by.yayauheny.entity.ItemEntity;
import by.yayauheny.entity.OrderEntity;
import by.yayauheny.entity.PaymentEntity;
import by.yayauheny.entity.UserEntity;
import by.yayauheny.entity.WalletEntity;
import by.yayauheny.enums.AuctionStatus;
import by.yayauheny.enums.BidHoldBalanceStatus;
import by.yayauheny.enums.BidStatus;
import by.yayauheny.enums.ItemConditionType;
import by.yayauheny.enums.ItemTransactionStatus;
import by.yayauheny.enums.OrderStatus;
import by.yayauheny.enums.PaymentStatus;
import by.yayauheny.enums.PaymentType;
import by.yayauheny.enums.Role;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class TestDataUtil {

  private TestDataUtil() {
    throw new IllegalStateException("Cannot create util class");
  }

  public static UserEntity getUser(String email) {
    return UserEntity.builder()
        .email(email)
        .password("securePassword123")
        .name("John")
        .lastName("Doe")
        .role(Role.USER)
        .address("123 Main St, Springfield, IL, 62701")
        .birthDate(LocalDate.of(1990, 5, 15))
        .build();
  }

  public static WalletEntity getWallet(UserEntity walletOwner) {
    var wallet = WalletEntity.builder()
        .owner(walletOwner)
        .currency("USD")
        .balance(BigDecimal.ZERO)
        .paymentInfo("no card provided")
        .build();
    walletOwner.setWallet(wallet);

    return wallet;
  }

  public static CategoryEntity getCategory(String name) {
    return CategoryEntity.builder()
        .name(name)
        .description("some description")
        .build();
  }

  public static ItemEntity getItem(String name, CategoryEntity category, UserEntity seller) {
    var item = ItemEntity.builder()
        .category(category)
        .seller(seller)
        .name(name)
        .description("some description")
        .condition(ItemConditionType.NEW)
        .paymentType(PaymentType.FIXED_PRICE)
        .status(ItemTransactionStatus.DRAFT)
        .price(BigDecimal.ONE)
        .currency("USD")
        .build();
    category.addItem(item);
    seller.addItemForSale(item);

    return item;
  }

  public static AuctionEntity getAuctionEntity(ItemEntity item, Clock clock) {
    var auction = AuctionEntity.builder()
        .item(item)
        .initialBidPrice(BigDecimal.ONE)
        .minBidStep(BigDecimal.ONE)
        .status(AuctionStatus.DRAFT)
        .finishedAt(Instant.now(clock).plus(7, ChronoUnit.DAYS))
        .build();
    item.addAuction(auction);

    return auction;
  }

  public static BidEntity getBid(AuctionEntity auction, UserEntity user, BigDecimal price) {
    var bid = BidEntity.builder()
        .auction(auction)
        .user(user)
        .status(BidStatus.ACTIVE)
        .price(price)
        .build();
    user.addBid(bid);
    auction.addBid(bid);

    return bid;
  }

  public static OrderEntity getOrder(UserEntity user, ItemEntity item, Clock clock) {
    var order = OrderEntity.builder()
        .user(user)
        .item(item)
        .status(OrderStatus.PENDING)
        .expireAt(Instant.now(clock).plus(30, ChronoUnit.MINUTES))
        .build();
    user.addOrder(order);
    item.setOrder(order);

    return order;
  }

  public static PaymentEntity getPayment(WalletEntity wallet, OrderEntity order) {
    var payment = PaymentEntity.builder()
        .wallet(wallet)
        .order(order)
        .amount(BigDecimal.TWO)
        .currency("USD")
        .status(PaymentStatus.PENDING)
        .build();
    wallet.addPayment(payment);
    order.addPayment(payment);

    return payment;
  }

  public static BidHoldBalanceEntity getBidHoldBalance(
      WalletEntity wallet,
      AuctionEntity auction,
      BidEntity bid
  ) {
    var bidHoldBalance = BidHoldBalanceEntity.builder()
        .wallet(wallet)
        .auction(auction)
        .bid(bid)
        .amount(bid.getPrice())
        .currency(auction.getItem().getCurrency())
        .status(BidHoldBalanceStatus.PENDING)
        .build();
    wallet.addBidHoldBalance(bidHoldBalance);

    return bidHoldBalance;
  }
}
