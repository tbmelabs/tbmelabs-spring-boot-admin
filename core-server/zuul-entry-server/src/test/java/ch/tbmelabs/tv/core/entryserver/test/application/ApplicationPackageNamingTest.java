package ch.tbmelabs.tv.core.entryserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ch.tbmelabs.tv.core.entryserver.Application;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAwareJunitTest;

public class ApplicationPackageNamingTest extends AbstractZuulApplicationContextAwareJunitTest {
  private static final String APPLICATION_PACKAGE_NAME = "ch.tbmelabs.tv.core.entryserver";

  @Test
  public void applicationPackageShouldMatchNamingConvention() {
    assertThat(Application.class.getPackage().getName()).isEqualTo(APPLICATION_PACKAGE_NAME)
        .withFailMessage("Do not rename this package as this may cause trouble with the naming conventions!");
  }
}