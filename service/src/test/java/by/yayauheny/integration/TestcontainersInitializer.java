package by.yayauheny.integration;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

public class TestcontainersInitializer implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      DockerImageName.parse("postgres:17"));

  static {
    postgres.setCommand("postgres", "-c", "fsync=off", "-c", "log_statement=all");
    Startables.deepStart(postgres).join();
  }

  @Override
  public void initialize(ConfigurableApplicationContext ctx) {
    TestPropertyValues.of(
        "spring.datasource.url=" + postgres.getJdbcUrl(),
        "spring.datasource.username=" + postgres.getUsername(),
        "spring.datasource.rawPassword=" + postgres.getPassword()
    ).applyTo(ctx.getEnvironment());
  }
}
