package ch.tbmelabs.core.servicediscovery.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicationContextAware;
import ch.tbmelabs.tv.core.servicediscovery.configuration.OAuth2SSOEurekaConfiguration;

public class EurekaConfigurationTest extends AbstractEurekaApplicationContextAware {
  @Test
  public void eurekaConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOEurekaConfiguration.class).hasAnnotation(Configuration.class);
    assertThat(OAuth2SSOEurekaConfiguration.class).hasAnnotation(EnableEurekaServer.class);
  }
}