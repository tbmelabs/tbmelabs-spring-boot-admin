package ch.tbmelabs.core.servicediscovery.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicationContextAware;
import ch.tbmelabs.tv.core.servicediscovery.Application;

public class ApplicationPackageNamingTest extends AbstractEurekaApplicationContextAware {
  private static final String APPLICATION_PACKAGE_NAME = "ch.tbmelabs.tv.core.servicediscovery";

  @Test
  public void applicationPackageShouldMatchNamingConvention() {
    assertThat(Application.class.getPackage().getName()).isEqualTo(APPLICATION_PACKAGE_NAME);
  }
}