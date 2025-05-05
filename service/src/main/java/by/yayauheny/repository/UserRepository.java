package by.yayauheny.repository;

import by.yayauheny.entity.UserEntity;
import by.yayauheny.repository.filter.FilterUserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<UserEntity, UUID>,
    FilterUserRepository,
    QuerydslPredicateExecutor<UserEntity> {

  boolean existsByEmail(String email);

  Optional<UserEntity> findByName(String name);
}
