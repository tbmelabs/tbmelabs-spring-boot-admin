package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ch.tbmelabs.tv.core.authorizationserver.configuration.WebMvcConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class WebMvcConfigurationIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Autowired
  private WebMvcConfiguration configuration;

  @Autowired
  private InternalResourceViewResolver bean;

  @Test
  public void internalResourceViewResolverBeanShouldEqualConfiguredViewResolver() {
    assertThat(configuration.viewResolver()).isEqualTo(bean);
  }
}