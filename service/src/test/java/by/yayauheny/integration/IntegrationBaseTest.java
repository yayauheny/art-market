package by.yayauheny.integration;

import static java.time.temporal.ChronoUnit.SECONDS;

import by.yayauheny.util.HibernateTestUtil;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class IntegrationBaseTest {

  protected static final Clock clock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
  protected static final PostgreSQLContainer<?> postgreSqlContainer;
  protected static final SessionFactory sessionFactory;
  protected Session session;
  protected Transaction transaction;

  static {
    postgreSqlContainer = configurePostgreSQLContainer();
    postgreSqlContainer.start();
    sessionFactory = getDbConfiguration().buildSessionFactory();
  }

  private static PostgreSQLContainer<?> configurePostgreSQLContainer() {
    return new PostgreSQLContainer<>("postgres:17")
        .withInitScript("init_tables.sql")
        .withStartupTimeout(Duration.of(30, SECONDS));
  }

  private static Configuration getDbConfiguration() {
    Configuration configuration = HibernateTestUtil.buildConfiguration();
    configuration.setProperty("hibernate.connection.url", postgreSqlContainer.getJdbcUrl());
    configuration.setProperty("hibernate.connection.username", postgreSqlContainer.getUsername());
    configuration.setProperty("hibernate.connection.password", postgreSqlContainer.getPassword());
    return configuration.configure();
  }

  @BeforeEach
  public void openSessionAndTransaction() {
    session = getSessionFactory().openSession();
    transaction = session.beginTransaction();
  }

  @AfterEach
  public void closeSessionAndTransaction() {
    if (transaction != null && transaction.isActive()) {
      transaction.rollback();
    }
    if (session != null && session.isOpen()) {
      session.close();
    }
  }

  protected static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
