package by.yayauheny.repository;

import by.yayauheny.entity.WalletEntity;
import jakarta.persistence.EntityManager;
import java.util.UUID;

public class WalletRepository extends RepositoryBase<UUID, WalletEntity> {

  public WalletRepository(EntityManager entityManager) {
    super(WalletEntity.class, entityManager);
  }
}
