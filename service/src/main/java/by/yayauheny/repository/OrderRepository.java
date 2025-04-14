package by.yayauheny.repository;

import by.yayauheny.entity.OrderEntity;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends RepositoryBase<UUID, OrderEntity> {

  public OrderRepository(EntityManager entityManager) {
    super(OrderEntity.class, entityManager);
  }
}
