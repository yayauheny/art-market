package by.yayauheny.util;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public final class HibernateTestUtil {

  private HibernateTestUtil() {
    throw new IllegalStateException("Cannot create util class");
  }

  public static Configuration buildConfiguration() {
    Configuration configuration = new Configuration();
    configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
    return configuration;
  }
}
