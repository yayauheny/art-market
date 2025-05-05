package by.yayauheny.integration;

import by.yayauheny.integration.annotation.IT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@IT
@RequiredArgsConstructor
public abstract class IntegrationBaseTest {

  protected static final Clock clock = Clock.fixed(Instant.now(), ZoneOffset.UTC);

  @Autowired
  protected EntityManager entityManager;

  @Autowired
  protected ObjectMapper objectMapper;
}
