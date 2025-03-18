package by.yayauheny.repository;

import by.yayauheny.entity.PaymentEntity;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository extends RepositoryBase<UUID, PaymentEntity> {

  @Autowired
  public PaymentRepository(EntityManager entityManager) {
    super(PaymentEntity.class, entityManager);
  }
}
