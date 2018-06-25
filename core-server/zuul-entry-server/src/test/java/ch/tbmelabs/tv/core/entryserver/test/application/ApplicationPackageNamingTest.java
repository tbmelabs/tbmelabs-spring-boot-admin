package ch.tbmelabs.tv.core.entryserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.entryserver.Application;
import org.junit.Test;

public class ApplicationPackageNamingTest {

  private static final String APPLICATION_PACKAGE_NAME = "ch.tbmelabs.tv.core.entryserver";

  @Test
  public void applicationPackageShouldMatchNamingConvention() {
    assertThat(Application.class.getPackage().getName()).isEqualTo(APPLICATION_PACKAGE_NAME);
  }
}
