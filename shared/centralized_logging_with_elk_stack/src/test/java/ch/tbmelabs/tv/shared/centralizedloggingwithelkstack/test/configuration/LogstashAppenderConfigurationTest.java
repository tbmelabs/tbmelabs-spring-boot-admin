package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.util.ReflectionTestUtils;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.LogstashAppenderConfiguration;

public class LogstashAppenderConfigurationTest {

  @Mock
  private LogstashAppenderConfiguration fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    LogstashAppenderConfiguration.setAppenderName("logstash");
    ReflectionTestUtils.setField(fixture, "applicationName", "TEST_APPLICATION");
    ReflectionTestUtils.setField(fixture, "logstashHost", "localhost");
    ReflectionTestUtils.setField(fixture, "logstashPort", 5000);

    doCallRealMethod().when(fixture).initBean();
  }

  @Test
  public void logstashAppenderConfigurationShouldBeAnnotated() {
    assertThat(LogstashAppenderConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void logstashAppenderConfigurationShouldThrowErrorIfApplicationNameIsNull() {
    ReflectionTestUtils.setField(fixture, "applicationName", null);

    assertThatThrownBy(() -> fixture.initBean()).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Application name may not be empty!");
  }

  @Test
  public void logstashAppenderShouldBeRegisteredToRootLogger() {
    fixture.initBean();

    assertThat(((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).getAppenders())
        .containsKey(LogstashAppenderConfiguration.getAppenderName());
    assertThat(((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).getAppenders()
        .get(LogstashAppenderConfiguration.getAppenderName())).isNotNull();
  }

  @Test
  public void appenderShouldHavePatternLayout() {
    Appender appender = ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger())
        .getAppenders().get(LogstashAppenderConfiguration.getAppenderName());

    assertThat(appender).isNotNull();
    assertThat(((PatternLayout) appender.getLayout()).getConversionPattern())
        .isEqualTo("application=TEST_APPLICATION; thread=%t; level=%-5p; package=%c; message=%m%n");
  }

  @Test
  public void appenderNameGetterAndSetterWorkAsExpected() {
    String appenderName = "anotherappender";

    assertThat(LogstashAppenderConfiguration.getAppenderName()).isEqualTo("logstash");

    LogstashAppenderConfiguration.setAppenderName(appenderName);

    assertThat(fixture).hasFieldOrPropertyWithValue("appenderName", appenderName);
  }
}
