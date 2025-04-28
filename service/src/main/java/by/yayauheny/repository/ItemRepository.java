package by.yayauheny.repository;


import by.yayauheny.entity.ItemEntity;
import by.yayauheny.repository.filter.FilterItemRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, UUID>,
    FilterItemRepository,
    QuerydslPredicateExecutor<ItemEntity> {

}
