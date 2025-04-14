package by.yayauheny.integration;

import jakarta.persistence.EntityManager;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class IntegrationBaseTest {

  protected static final Clock clock = Clock.fixed(Instant.now(), ZoneOffset.UTC);

  @Autowired
  protected EntityManager entityManager;
}
