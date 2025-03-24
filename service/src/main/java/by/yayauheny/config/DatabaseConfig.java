package by.yayauheny.config;

import by.yayauheny.util.HibernateUtil;
import java.lang.reflect.Proxy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

  @Bean
  public SessionFactory sessionFactory() {
    return HibernateUtil.buildConfiguration().buildSessionFactory();
  }

  @Bean
  public Session session(SessionFactory sessionFactory) {
    return (Session) Proxy.newProxyInstance(
        SessionFactory.class.getClassLoader(), new Class[]{Session.class},
        (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
  }
}
