package by.yayauheny.repository.filter;

import static by.yayauheny.entity.QItemEntity.itemEntity;

import by.yayauheny.dto.filter.ItemFilter;
import by.yayauheny.entity.ItemEntity;
import by.yayauheny.entity.ItemEntity_;
import by.yayauheny.enums.ItemTransactionStatus;
import by.yayauheny.enums.OperatorCompareType;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilterItemRepositoryImpl implements FilterItemRepository {

  private final EntityManager entityManager;

  @Override
  public List<ItemEntity> findAllByFilter(@NonNull ItemFilter filter) {
    var itemFilterPredicate = QPredicate.builder()
        .add(filter.getCategoryName(), itemEntity.category.name::eq)
        .add(filter.getSellerId(), itemEntity.seller.id::eq)
        .add(filter.getName(), itemEntity.name::containsIgnoreCase)
        .add(filter.getDescription(), itemEntity.description::containsIgnoreCase)
        .add(filter.getCondition(), itemEntity.condition::eq)
        .add(filter.getPaymentType(), itemEntity.paymentType::eq)
        .add(filter.getStatus(), itemEntity.status::eq);
    if (filter.getPrice() != null) {
      itemFilterPredicate.add(filter.getPrice().getValue(),
          filter.getPrice().getOperator() == OperatorCompareType.GOE
              ? itemEntity.price::goe
              : itemEntity.price::loe);
    }
    var builtItemPredicate = itemFilterPredicate.buildAnd();

    return new JPAQuery<ItemEntity>(entityManager)
        .select(itemEntity)
        .from(itemEntity)
        .where(builtItemPredicate)
        .fetch();
  }

  @Override
  public long countSoldItemsBetweenDates(Instant from, Instant to) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> query = cb.createQuery(Long.class);
    Root<ItemEntity> item = query.from(ItemEntity.class);
    Predicate dateRangePredicate = cb.between(item.get(ItemEntity_.updatedAt), from, to);
    Predicate statusPredicate = cb.equal(item.get(ItemEntity_.status), ItemTransactionStatus.SOLD);
    query.select(cb.count(item))
        .where(cb.and(dateRangePredicate, statusPredicate));

    return entityManager
        .createQuery(query)
        .getSingleResult();
  }
}
