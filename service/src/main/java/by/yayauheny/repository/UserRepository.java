package by.yayauheny.repository;

import by.yayauheny.entity.UserEntity;
import jakarta.persistence.EntityManager;
import java.util.UUID;

public class UserRepository extends RepositoryBase<UUID, UserEntity> {

  public UserRepository(EntityManager entityManager) {
    super(UserEntity.class, entityManager);
  }
}
