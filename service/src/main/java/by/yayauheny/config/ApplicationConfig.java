package by.yayauheny.config;

import by.yayauheny.util.HibernateUtil;
import java.lang.reflect.Proxy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = {"by.yayauheny"})
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

  @Bean
  public Configuration configuration(){
    return HibernateUtil.buildConfiguration();
  }

  @Bean
  public SessionFactory sessionFactory(Configuration configuration) {
    return configuration.buildSessionFactory();
  }

  @Bean
  public Session session(SessionFactory sessionFactory) {
    return (Session) Proxy.newProxyInstance(
        SessionFactory.class.getClassLoader(), new Class[]{Session.class},
        (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
  }
}
