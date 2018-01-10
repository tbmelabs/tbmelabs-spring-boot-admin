package ch.tbmelabs.tv.core.authorizationserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ch.tbmelabs.tv.core.authorizationserver.ApplicationContextHolder;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ApplicationContextHolderTest extends AbstractOAuth2AuthorizationApplicationContextAware
    implements ApplicationContextAware {
  private ApplicationContext gettedApplicationContext;

  @Autowired
  private ApplicationContext injectedApplicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.gettedApplicationContext = applicationContext;
  }

  @Test
  public void injectedApplicationContextShouldEqualGettedApplicationContext() {
    assertThat(injectedApplicationContext).isEqualTo(gettedApplicationContext).withFailMessage(
        "The %s in %s is not properly getted!", ApplicationContext.class, ApplicationContextHolder.class);
  }
}