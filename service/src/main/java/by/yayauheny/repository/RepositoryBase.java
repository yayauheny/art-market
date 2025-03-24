package by.yayauheny.repository;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E> {

  private final Class<E> clazz;

  @Getter
  private final EntityManager entityManager;

  public E save(E entity) {
    entityManager.persist(entity);
    return entity;
  }

  public void delete(E entity) {
    entityManager.remove(entity);
    entityManager.flush();
  }

  public void update(E entity) {
    entityManager.merge(entity);
  }

  public Optional<E> findById(K id) {
    return Optional.ofNullable(entityManager.find(clazz, id));
  }

  public List<E> findAll() {
    var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
    criteria.from(clazz);
    return entityManager.createQuery(criteria)
        .getResultList();
  }
}
