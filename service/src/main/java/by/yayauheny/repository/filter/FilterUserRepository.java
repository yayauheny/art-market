package by.yayauheny.repository.filter;

import by.yayauheny.dto.filter.UserFilter;
import by.yayauheny.entity.UserEntity;
import java.util.List;

public interface FilterUserRepository {

  List<UserEntity> findAllByFilter(UserFilter filter);
}
