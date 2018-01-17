package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.authorizationserver.configuration.EurekaConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class EurekaConfigurationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Test
  public void eurekaConfigurationShouldBeAnnotated() {
    assertThat(EurekaConfiguration.class).hasAnnotation(Configuration.class).hasAnnotation(EnableEurekaClient.class);
  }
}