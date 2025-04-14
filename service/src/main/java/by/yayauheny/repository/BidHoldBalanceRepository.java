package by.yayauheny.repository;

import by.yayauheny.entity.BidHoldBalanceEntity;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BidHoldBalanceRepository extends RepositoryBase<UUID, BidHoldBalanceEntity> {

  public BidHoldBalanceRepository(EntityManager entityManager) {
    super(BidHoldBalanceEntity.class, entityManager);
  }
}
