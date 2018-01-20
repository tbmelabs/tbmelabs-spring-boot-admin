package ch.tbmelabs.tv.core.entryserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ch.tbmelabs.tv.core.entryserver.Application;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAware;

public class ApplicationPackageNamingTest extends AbstractZuulApplicationContextAware {
  private static final String APPLICATION_PACKAGE_NAME = "ch.tbmelabs.tv.core.entryserver";

  @Test
  public void applicationPackageShouldMatchNamingConvention() {
    assertThat(Application.class.getPackage().getName()).isEqualTo(APPLICATION_PACKAGE_NAME);
  }
}