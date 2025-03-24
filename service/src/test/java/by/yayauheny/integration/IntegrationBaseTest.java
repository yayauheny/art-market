package by.yayauheny.integration;

import by.yayauheny.integration.config.ApplicationTestConfig;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class IntegrationBaseTest {

  protected static final Clock clock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
  protected static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
  protected Session session;

  static {
    context.register(ApplicationTestConfig.class);
    context.refresh();
  }

  @BeforeEach
  public void init() {
    session = context.getBean(Session.class);
  }
}
