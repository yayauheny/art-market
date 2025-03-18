package by.yayauheny.util;

import lombok.experimental.UtilityClass;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

  public static Configuration buildConfiguration() {
    Configuration configuration = new Configuration();
    configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
    return configuration;
  }
}
