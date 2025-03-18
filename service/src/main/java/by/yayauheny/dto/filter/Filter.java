package by.yayauheny.dto.filter;

import by.yayauheny.enums.OperatorCompareType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filter<T extends Comparable<T>> {
  private T value;
  private OperatorCompareType operator;
}
