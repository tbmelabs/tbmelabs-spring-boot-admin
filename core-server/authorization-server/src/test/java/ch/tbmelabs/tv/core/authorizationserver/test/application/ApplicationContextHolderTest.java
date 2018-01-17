package ch.tbmelabs.tv.core.authorizationserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import ch.tbmelabs.tv.core.authorizationserver.ApplicationContextHolder;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ApplicationContextHolderTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Autowired
  private ApplicationContext injectedApplicationContext;

  @Test
  public void injectedApplicationContextShouldEqualGettedApplicationContext() {
    assertThat(ApplicationContextHolder.getApplicationContext()).isEqualTo(injectedApplicationContext);
  }
}