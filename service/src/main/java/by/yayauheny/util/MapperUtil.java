package by.yayauheny.util;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Named("MapperUtil")
@Component
@RequiredArgsConstructor
public class MapperUtil {

  private final PasswordEncoder passwordEncoder;

  @Named("encodePassword")
  public String encodePassword(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  @Named("normalizeEmail")
  public String normalizeEmail(String email) {
    return StringUtil.normalizeEmail(email);
  }
}
