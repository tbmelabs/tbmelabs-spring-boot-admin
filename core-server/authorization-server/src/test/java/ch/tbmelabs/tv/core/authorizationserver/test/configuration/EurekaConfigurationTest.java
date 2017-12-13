package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.authorizationserver.configuration.EurekaConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;

public class EurekaConfigurationTest extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  @Test
  public void eurekaConfigurationShouldBeAnnotated() {
    assertThat(EurekaConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", EurekaConfiguration.class,
        Configuration.class);

    assertThat(EurekaConfiguration.class).hasAnnotation(EnableEurekaClient.class).withFailMessage(
        "Annotate %s with %s to enable the eureka client!", EurekaConfiguration.class, EnableEurekaClient.class);
  }
}