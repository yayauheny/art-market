package by.yayauheny.repository;


import static by.yayauheny.entity.QItemEntity.itemEntity;

import by.yayauheny.dto.filter.ItemFilterDto;
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
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository extends RepositoryBase<UUID, ItemEntity> {

  @Autowired
  public ItemRepository(EntityManager entityManager) {
    super(ItemEntity.class, entityManager);
  }

  public List<ItemEntity> findItemsByFilter(ItemFilterDto itemFilter) {
    var itemFilterPredicate = QPredicate.builder()
        .add(itemFilter.getCategoryName(), itemEntity.category.name::eq)
        .add(itemFilter.getSellerId(), itemEntity.seller.id::eq)
        .add(itemFilter.getName(), itemEntity.name::containsIgnoreCase)
        .add(itemFilter.getDescription(), itemEntity.description::containsIgnoreCase)
        .add(itemFilter.getCondition(), itemEntity.condition::eq)
        .add(itemFilter.getPaymentType(), itemEntity.paymentType::eq)
        .add(itemFilter.getStatus(), itemEntity.status::eq)
        .add(itemFilter.getPrice().getValue(),
            itemFilter.getPrice().getOperator() == OperatorCompareType.GOE
                ? itemEntity.price::goe
                : itemEntity.price::loe)
        .buildAnd();

    return new JPAQuery<ItemEntity>(getEntityManager())
        .select(itemEntity)
        .from(itemEntity)
        .where(itemFilterPredicate)
        .fetch();
  }

  public long countSoldItemsBetweenDates(Instant from, Instant to) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<Long> query = cb.createQuery(Long.class);
    Root<ItemEntity> item = query.from(ItemEntity.class);
    Predicate dateRangePredicate = cb.between(item.get(ItemEntity_.updatedAt), from, to);
    Predicate statusPredicate = cb.equal(item.get(ItemEntity_.status), ItemTransactionStatus.SOLD);

    query.select(cb.count(item))
        .where(cb.and(dateRangePredicate, statusPredicate));

    return getEntityManager().createQuery(query).getSingleResult();
  }
}
