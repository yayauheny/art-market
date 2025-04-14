package by.yayauheny.integration.annotation;

import by.yayauheny.integration.TestcontainersInitializer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = TestcontainersInitializer.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableTestcontainers {

}
