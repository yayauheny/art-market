package by.yayauheny.repository.filter;

import static by.yayauheny.entity.QUserEntity.userEntity;

import by.yayauheny.dto.filter.UserFilter;
import by.yayauheny.entity.ItemEntity;
import by.yayauheny.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

  private final EntityManager entityManager;

  @Override
  public List<UserEntity> findAllByFilter(UserFilter filter) {
    if (filter == null) {
      throw new IllegalArgumentException("Filter cannot be empty");
    }
    var predicate = QPredicate.builder()
        .add(filter.getName(), userEntity.name::likeIgnoreCase)
        .add(filter.getLastName(), userEntity.name::likeIgnoreCase)
        .add(filter.getRole(), userEntity.role::eq)
        .buildAnd();
    return new JPAQuery<ItemEntity>(entityManager)
        .select(userEntity)
        .from(userEntity)
        .where(predicate)
        .fetch();
  }
}
