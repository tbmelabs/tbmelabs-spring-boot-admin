package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.entryserver.configuration.OAuth2SSOSecurityConfiguration;
import org.junit.Test;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;

public class OAuth2SSOSecurityConfigurationTest {

  @Test
  public void oauth2SSOZuulProxyConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOSecurityConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableOAuth2Sso.class);
  }

  @Test
  public void oauth2SSOZuulProxyConfigurationShouldHavePublicConstructor() {
    assertThat(new OAuth2SSOSecurityConfiguration()).isNotNull();
  }
}
