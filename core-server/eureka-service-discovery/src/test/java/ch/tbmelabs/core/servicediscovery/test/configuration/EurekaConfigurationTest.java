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
    assertThat(OAuth2SSOEurekaConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", OAuth2SSOEurekaConfiguration.class,
        Configuration.class);

    assertThat(OAuth2SSOEurekaConfiguration.class).hasAnnotation(EnableEurekaServer.class).withFailMessage(
        "Annotate %s with %s to enable the eureka server!", OAuth2SSOEurekaConfiguration.class, EnableEurekaServer.class);
  }
}