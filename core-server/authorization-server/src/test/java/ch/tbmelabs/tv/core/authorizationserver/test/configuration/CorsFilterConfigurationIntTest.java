package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.CorsFilter;

import ch.tbmelabs.tv.core.authorizationserver.configuration.CorsFilterConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class CorsFilterConfigurationIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Autowired
  private CorsFilterConfiguration configuration;

  @Autowired
  private CorsFilter bean;

  @Test
  public void primaryRegisteredBeanShouldEqualConfiguredCorsFilter() {
    assertThat(configuration.logoutCorsFilter()).isEqualTo(bean);
  }
}