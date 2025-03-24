package by.yayauheny.repository;

import by.yayauheny.entity.AuctionEntity;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuctionRepository extends RepositoryBase<UUID, AuctionEntity> {

  @Autowired
  public AuctionRepository(EntityManager entityManager) {
    super(AuctionEntity.class, entityManager);
  }
}
