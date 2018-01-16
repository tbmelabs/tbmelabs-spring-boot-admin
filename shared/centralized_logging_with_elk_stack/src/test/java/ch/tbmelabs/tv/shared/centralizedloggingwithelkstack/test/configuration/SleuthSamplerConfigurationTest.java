package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.SleuthSamplerConfiguration;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.AbstractCentralizedLoggingApplicationContextAware;

public class SleuthSamplerConfigurationTest extends AbstractCentralizedLoggingApplicationContextAware {
  private static final String DEFAULT_SAMPLER_NAME = "defaultSampler";

  @Autowired
  private SleuthSamplerConfiguration sleuthSamplerConfiguration;

  @Autowired
  @Qualifier(DEFAULT_SAMPLER_NAME)
  private AlwaysSampler injectedSampler;

  @Test
  public void sleuthSamplerConfigurationShouldBeAnnotated() {
    assertThat(SleuthSamplerConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", SleuthSamplerConfiguration.class,
        Configuration.class);
  }

  @Test
  public void samplerBeanShouldReturnAnAlwaysSampler()
      throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    Method alwaysSampler = SleuthSamplerConfiguration.class.getDeclaredMethod(DEFAULT_SAMPLER_NAME, new Class[] {});

    assertThat(alwaysSampler.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(alwaysSampler.invoke(sleuthSamplerConfiguration, new Object[] {})).isEqualTo(injectedSampler)
        .withFailMessage("The configured %s should equal the primary registered %s in spring context!",
            SleuthSamplerConfiguration.class, Bean.class);
  }
}