package by.yayauheny.integration.config;

import by.yayauheny.config.ApplicationConfig;
import by.yayauheny.util.HibernateTestUtil;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(ApplicationConfig.class)
public class ApplicationTestConfig {

  @Bean
  public Configuration configuration() {
    return HibernateTestUtil.buildConfiguration();
  }
}
