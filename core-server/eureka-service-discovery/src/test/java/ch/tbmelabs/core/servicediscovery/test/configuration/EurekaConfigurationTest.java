package ch.tbmelabs.core.servicediscovery.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicatoinContextAwareJunitTest;
import ch.tbmelabs.tv.core.servicediscovery.configuration.EurekaConfiguration;

public class EurekaConfigurationTest extends AbstractEurekaApplicatoinContextAwareJunitTest {
  @Test
  public void eurekaConfigurationShouldBeAnnotated() {
    assertThat(EurekaConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", EurekaConfiguration.class,
        Configuration.class);

    assertThat(EurekaConfiguration.class).hasAnnotation(EnableEurekaServer.class).withFailMessage(
        "Annotate %s with %s to enable the eureka server!", EurekaConfiguration.class, EnableEurekaServer.class);
  }
}