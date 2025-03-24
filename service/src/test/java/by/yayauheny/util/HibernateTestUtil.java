package by.yayauheny.util;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import lombok.experimental.UtilityClass;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class HibernateTestUtil {

  protected static final PostgreSQLContainer<?> postgreSqlContainer;

  static {
    postgreSqlContainer = configurePostgreSQLContainer();
    postgreSqlContainer.start();
  }

  private static PostgreSQLContainer<?> configurePostgreSQLContainer() {
    return new PostgreSQLContainer<>("postgres:17")
        .withInitScript("init_tables.sql")
        .withStartupTimeout(Duration.of(30, SECONDS));
  }

  public static Configuration buildConfiguration() {
    Configuration configuration = new Configuration();
    configuration.setProperty("hibernate.connection.url", postgreSqlContainer.getJdbcUrl());
    configuration.setProperty("hibernate.connection.username", postgreSqlContainer.getUsername());
    configuration.setProperty("hibernate.connection.password", postgreSqlContainer.getPassword());
    configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
    return configuration.configure();
  }
}
