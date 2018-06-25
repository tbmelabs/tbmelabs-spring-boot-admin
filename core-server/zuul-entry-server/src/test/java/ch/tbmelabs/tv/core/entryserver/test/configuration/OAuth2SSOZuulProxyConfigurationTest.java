package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.entryserver.configuration.OAuth2SSOZuulProxyConfiguration;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.test.util.ReflectionTestUtils;

public class OAuth2SSOZuulProxyConfigurationTest {

  private final MockEnvironment mockEnvironment = new MockEnvironment();

  @Spy
  @InjectMocks
  private OAuth2SSOZuulProxyConfiguration fixture;

  @Before
  public void beforeTestSetUp() throws Exception {
    initMocks(this);

    mockEnvironment.setActiveProfiles(SpringApplicationProfile.DEV);
    ReflectionTestUtils.setField(fixture, "environment", mockEnvironment);
  }

  @Test
  public void oauth2SSOZuulProxyConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableZuulProxy.class).hasAnnotation(EnableOAuth2Sso.class);
  }

  @Test
  public void oauth2SSOZuulProxyConfigurationHasPublicConstructor() {
    assertThat(new OAuth2SSOZuulProxyConfiguration()).isNotNull();
  }

  @Test
  public void configurationShouldDebugHttpRequestsIfDevelopmentProfileIsActive() throws Exception {
    WebSecurity security = Mockito.mock(WebSecurity.class);

    fixture.configure(security);

    verify(security, times(1)).debug(true);
  }
}
