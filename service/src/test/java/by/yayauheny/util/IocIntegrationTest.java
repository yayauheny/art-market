package by.yayauheny.util;

import by.yayauheny.integration.config.TestDatabaseConfig;
import java.lang.reflect.Field;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class IocIntegrationTest implements BeforeEachCallback {

  private final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
      TestDatabaseConfig.class);

  @Override
  public void beforeEach(ExtensionContext context) {
    Object testInstance = context.getRequiredTestInstance();
    injectDependencies(testInstance);
  }

  private void injectDependencies(Object testInstance) {
    Field[] fields = testInstance.getClass().getDeclaredFields();
    for (Field field : fields) {
      Object bean = applicationContext.getBean(field.getType());
      field.setAccessible(true);
      try {
        field.set(testInstance, bean);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Failed to inject dependencies into " + field.getName(), e);
      }
    }
  }
}
