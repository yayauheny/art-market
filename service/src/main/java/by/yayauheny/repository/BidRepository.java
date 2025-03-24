package by.yayauheny.repository;

import by.yayauheny.entity.BidEntity;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BidRepository extends RepositoryBase<UUID, BidEntity> {

  @Autowired
  public BidRepository(EntityManager entityManager) {
    super(BidEntity.class, entityManager);
  }
}
