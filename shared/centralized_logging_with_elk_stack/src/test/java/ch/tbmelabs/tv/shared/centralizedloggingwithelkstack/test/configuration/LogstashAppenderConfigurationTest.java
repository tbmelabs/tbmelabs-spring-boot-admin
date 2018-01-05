package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.LogstashAppenderConfiguration;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.AbstractCentralizedLoggingApplicationContextAwareJunitTest;

public class LogstashAppenderConfigurationTest extends AbstractCentralizedLoggingApplicationContextAwareJunitTest {
  @Test
  public void logstashAppenderConfigurationShouldBeAnnotated() {
    assertThat(LogstashAppenderConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", LogstashAppenderConfiguration.class,
        Configuration.class);
  }
}