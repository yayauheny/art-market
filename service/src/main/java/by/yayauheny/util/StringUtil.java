package by.yayauheny.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

  public static String normalizeEmail(@NonNull String email) {
    if (!email.isBlank()) {
      return email.trim().toLowerCase();
    }
    return email;
  }
}
