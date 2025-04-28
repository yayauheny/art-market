package by.yayauheny.repository;


import by.yayauheny.entity.ItemEntity;
import by.yayauheny.entity.ItemEntity_;
import by.yayauheny.enums.ItemTransactionStatus;
import by.yayauheny.repository.filter.FilterItemRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, UUID>,
    FilterItemRepository,
    QuerydslPredicateExecutor<ItemEntity> {




}
