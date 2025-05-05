package by.yayauheny.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ADMIN, USER, SELLER;

  @Override
  public String getAuthority() {
    return name();
  }
}
