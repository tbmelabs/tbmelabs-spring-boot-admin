package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;

import ch.tbmelabs.tv.core.authorizationserver.configuration.CorsFilterConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class CorsFilterConfigurationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String CORS_FILTER_NAME = "logoutCorsFilter";

  @Autowired
  private CorsFilterConfiguration corsFilterConfiguration;

  @Autowired
  @Qualifier(CORS_FILTER_NAME)
  private CorsFilter injectedCorsFilter;

  @Test
  public void corsFilterConfigurationShouldBeAnnotated() {
    assertThat(CorsFilterConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", CorsFilterConfiguration.class,
        Configuration.class);
  }

  @Test
  public void corsFilterBeanShouldReturnACorsFilter()
      throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    Method corsFilter = CorsFilterConfiguration.class.getDeclaredMethod(CORS_FILTER_NAME, new Class[] {});

    assertThat(corsFilter.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(corsFilter.invoke(corsFilterConfiguration, new Object[] {})).isEqualTo(injectedCorsFilter)
        .withFailMessage("The configured %s should equal the primary registered %s in spring context!",
            CorsFilter.class, Bean.class);
  }
}