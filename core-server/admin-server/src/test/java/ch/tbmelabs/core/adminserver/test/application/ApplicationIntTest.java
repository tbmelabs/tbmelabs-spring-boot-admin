package ch.tbmelabs.core.adminserver.test.application;

import ch.tbmelabs.core.adminserver.test.AbstractAdminServerContextAwareTest;
import ch.tbmelabs.tv.core.adminserver.Application;
import org.junit.Test;

public class ApplicationIntTest extends AbstractAdminServerContextAwareTest {

  @Test
  public void applicationContextLoads() {
  }

  @Test
  public void publicStaticVoidMainShouldStartSpringApplication() {
    Application.main(new String[]{});
  }
}
