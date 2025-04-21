package by.yayauheny.repository.filter;

import by.yayauheny.dto.filter.ItemFilter;
import by.yayauheny.entity.ItemEntity;
import java.time.Instant;
import java.util.List;

public interface FilterItemRepository {

  List<ItemEntity> findAllByFilter(ItemFilter filter);

  long countSoldItemsBetweenDates(Instant from, Instant to);
}
