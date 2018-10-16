package ch.tbmelabs.springbootadmin.test.application;

import ch.tbmelabs.springbootadmin.Application;
import ch.tbmelabs.springbootadmin.test.AbstractSpringBootAdminServerContextAwareTest;
import org.junit.Test;

public class ApplicationIntTest extends AbstractSpringBootAdminServerContextAwareTest {

  @Test
  public void applicationContextLoads() {
  }

  @Test
  public void publicStaticVoidMainShouldStartSpringApplication() {
    Application.main(new String[]{"--spring.profiles.active=test"});
  }
}
