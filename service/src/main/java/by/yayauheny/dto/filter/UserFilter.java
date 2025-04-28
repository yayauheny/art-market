package by.yayauheny.dto.filter;

import by.yayauheny.enums.Role;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

  String name;
  String lastName;
  Role role;
  LocalDate birthDate;
}
