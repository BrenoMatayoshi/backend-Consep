package br.com.consep.api.infra.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
    SpringContext.applicationContext = applicationContext;
  }

  public static <T> T getBean(@NonNull Class<T> type) {
    if (applicationContext == null) {
      return null;
    }

    return applicationContext.getBean(type);
  }
}