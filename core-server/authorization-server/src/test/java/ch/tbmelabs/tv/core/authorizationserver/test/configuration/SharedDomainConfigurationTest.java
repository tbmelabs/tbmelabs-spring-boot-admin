package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.authorizationserver.configuration.SharedDomainConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;

public class SharedDomainConfigurationTest extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final String SHARED_ENTITIES_PACKAGE_ROOT = "ch.tbmelabs.tv.shared.domain.authentication";

  @Test
  public void sharedDomainConfigurationTestShouldBeAnnotated() {
    assertThat(SharedDomainConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", SharedDomainConfiguration.class,
        Configuration.class);

    assertThat(SharedDomainConfiguration.class).hasAnnotation(EntityScan.class).withFailMessage(
        "Annotate %s with %s to scan for shared entities!", SharedDomainConfiguration.class, EntityScan.class);

    assertThat(SharedDomainConfiguration.class.getDeclaredAnnotation(EntityScan.class).basePackages())
        .containsExactly(SHARED_ENTITIES_PACKAGE_ROOT).withFailMessage("The %s should scan exactly the package \"%s\"!",
            SharedDomainConfiguration.class, SHARED_ENTITIES_PACKAGE_ROOT);
  }
}