package by.yayauheny.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

  private HibernateUtil() {
    throw new IllegalStateException("Cannot create util class");
  }

  public static SessionFactory buildSessionFactory() {
    Configuration config = new Configuration();
    config.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
    config.configure();
    return config.buildSessionFactory();
  }
}
