package by.yayauheny.integration.config;

import by.yayauheny.util.HibernateTestUtil;
import java.lang.reflect.Proxy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "by.yayauheny.repository")
public class TestDatabaseConfig {

  @Bean
  public SessionFactory sessionFactory() {
    return HibernateTestUtil.buildConfiguration().buildSessionFactory();
  }

  @Bean
  public Session session(SessionFactory sessionFactory) {
    return (Session) Proxy.newProxyInstance(
        SessionFactory.class.getClassLoader(),
        new Class[]{Session.class},
        (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args)
    );
  }
}
