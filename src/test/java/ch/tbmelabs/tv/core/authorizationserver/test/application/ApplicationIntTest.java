package ch.tbmelabs.tv.core.authorizationserver.test.application;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import org.junit.Test;

public class ApplicationIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  @Test
  public void applicationContextLoads() {
  }

  @Test
  public void publicStaticVoidMainShouldStartSpringApplication() {
    Application.main(new String[]{"--spring.profiles.active=test,no-redis,no-mail"});
  }
}
