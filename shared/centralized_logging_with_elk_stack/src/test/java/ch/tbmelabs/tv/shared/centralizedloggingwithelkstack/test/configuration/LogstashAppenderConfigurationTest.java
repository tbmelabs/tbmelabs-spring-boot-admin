package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.LogstashAppenderConfiguration;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.AbstractCentralizedLoggingApplicationContextAware;

public class LogstashAppenderConfigurationTest extends AbstractCentralizedLoggingApplicationContextAware {
  private static final String LOGSTASH_APPENDER_NAME = "logstash";

  @Test
  public void logstashAppenderConfigurationShouldBeAnnotated() {
    assertThat(LogstashAppenderConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void logstashAppenderShouldBeRegisteredToRootLogger() {
    assertThat(((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).getAppenders())
        .containsKey(LOGSTASH_APPENDER_NAME);
    assertThat(
        ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).getAppenders().get(LOGSTASH_APPENDER_NAME))
            .isNotNull();
  }
}