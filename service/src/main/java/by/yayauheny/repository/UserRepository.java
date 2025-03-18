package by.yayauheny.repository;

import by.yayauheny.entity.UserEntity;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends RepositoryBase<UUID, UserEntity> {

  @Autowired
  public UserRepository(EntityManager entityManager) {
    super(UserEntity.class, entityManager);
  }
}
