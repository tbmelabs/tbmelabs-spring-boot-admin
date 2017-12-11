package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.entryserver.configuration.EurekaConfiguration;
import ch.tbmelabs.tv.core.entryserver.test.ZuulEntryServerTest;

public class EurekaConfigurationTest implements ZuulEntryServerTest {
  @Test
  public void eurekaConfigurationShouldBeAnnotated() {
    assertThat(EurekaConfiguration.class).hasAnnotation(Configuration.class)
        .withFailMessage("Annotate " + EurekaConfiguration.class + " with " + Configuration.class
            + " to make it scannable for the spring application!");

    assertThat(EurekaConfiguration.class).hasAnnotation(EnableEurekaClient.class).withFailMessage("Annotate "
        + EurekaConfiguration.class + " with " + EnableEurekaClient.class + " to discover the active eureka server!");
  }
}