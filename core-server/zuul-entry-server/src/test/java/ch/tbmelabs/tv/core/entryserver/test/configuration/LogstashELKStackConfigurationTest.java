package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ch.tbmelabs.tv.core.entryserver.configuration.LogstashELKStackConfiguration;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAwareJunitTest;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class LogstashELKStackConfigurationTest extends AbstractZuulApplicationContextAwareJunitTest {
  @Test
  public void logstashELKStackConfigurationShouldBeAnnotated() {
    assertThat(LogstashELKStackConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", LogstashELKStackConfiguration.class,
        Configuration.class);

    assertThat(LogstashELKStackConfiguration.class.getDeclaredAnnotation(Profile.class)).isNotNull();

    assertThat(LogstashELKStackConfiguration.class.getDeclaredAnnotation(Profile.class).value())
        .containsAll(Arrays.asList(new String[] { SpringApplicationProfile.ELK }))
        .withFailMessage("The @%s annotation should allow the user to decide if he wants to use %s or not!",
            Profile.class, LogstashELKStackConfiguration.class);
  }
}