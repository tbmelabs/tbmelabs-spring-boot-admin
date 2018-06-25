package ch.tbmelabs.core.servicediscovery.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.servicediscovery.Application;
import org.junit.Test;

public class ApplicationPackageNamingTest {

  private static final String APPLICATION_PACKAGE_NAME = "ch.tbmelabs.tv.core.servicediscovery";

  @Test
  public void applicationPackageShouldMatchNamingConvention() {
    assertThat(Application.class.getPackage().getName()).isEqualTo(APPLICATION_PACKAGE_NAME);
  }
}
