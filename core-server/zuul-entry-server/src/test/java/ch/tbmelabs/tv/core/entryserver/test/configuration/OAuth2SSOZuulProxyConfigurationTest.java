package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import ch.tbmelabs.tv.core.entryserver.configuration.OAuth2SSOZuulProxyConfiguration;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class OAuth2SSOZuulProxyConfigurationTest {
  @Mock
  private WebSecurity webSecurity;

  @Mock
  private Environment environment;

  @Spy
  @InjectMocks
  private OAuth2SSOZuulProxyConfiguration fixture;

  @Before
  public void beforeTestSetUp() throws Exception {
    initMocks(this);

    when(environment.getActiveProfiles()).thenReturn(new String[] { SpringApplicationProfile.DEV });

    doCallRealMethod().when(fixture).configure(webSecurity);
  }

  @Test
  public void oauth2SSOZuulProxyConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableZuulProxy.class).hasAnnotation(EnableOAuth2Sso.class);
  }

  @Test
  public void configurationShouldDebugHttpRequestsIfDevelopmentProfileIsActive() throws Exception {
    fixture.configure(webSecurity);

    verify(webSecurity, times(1)).debug(true);
  }
}