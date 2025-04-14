package by.yayauheny.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class IocIntegrationTest implements BeforeEachCallback, ApplicationContextAware {

  protected Session session;
  protected Transaction transaction;
  protected ApplicationContext context;

  @Override
  public void beforeEach(ExtensionContext context) {
    Object testInstance = context.getRequiredTestInstance();
  }

  @BeforeEach
  public void openSessionAndTransaction() {
    transaction = session.beginTransaction();
  }

  @AfterEach
  public void closeSessionAndTransaction() {
    transaction.rollback();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
    session = applicationContext.getBean(Session.class);
  }
}
