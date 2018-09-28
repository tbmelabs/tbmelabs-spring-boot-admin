package ch.tbmelabs.configurationserver.test.application;

import ch.tbmelabs.configurationserver.Application;
import ch.tbmelabs.configurationserver.test.AbstractConfigurationServerContextAwareTest;
import org.junit.Test;

public class ApplicationIntTest extends AbstractConfigurationServerContextAwareTest {

  @Test
  public void applicationContextLoads() {
  }

  @Test
  public void publicStaticVoidMainShouldStartSpringApplication() {
    Application.main(new String[]{"--spring.profiles.active=test"});
  }
}
