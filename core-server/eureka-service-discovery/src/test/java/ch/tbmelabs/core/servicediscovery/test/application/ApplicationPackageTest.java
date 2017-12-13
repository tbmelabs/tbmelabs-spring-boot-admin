package ch.tbmelabs.core.servicediscovery.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicatoinContextAwareJunitTest;
import ch.tbmelabs.tv.core.servicediscovery.Application;

public class ApplicationPackageTest extends AbstractEurekaApplicatoinContextAwareJunitTest {
  private static final String APPLICATION_PACKAGE_NAME = "ch.tbmelabs.tv.core.servicediscovery";

  @Test
  public void applicationPackageShouldMatchNamingConvention() {
    assertThat(Application.class.getPackage().getName()).isEqualTo(APPLICATION_PACKAGE_NAME)
        .withFailMessage("Do not rename this package as this may cause trouble with the naming conventions!");
  }
}