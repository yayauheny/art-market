package by.yayauheny.repository;

import by.yayauheny.entity.CategoryEntity;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository extends RepositoryBase<UUID, CategoryEntity> {

  public CategoryRepository(EntityManager entityManager) {
    super(CategoryEntity.class, entityManager);
  }
}
