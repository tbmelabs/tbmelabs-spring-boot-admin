package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import ch.tbmelabs.tv.core.authorizationserver.configuration.OAuth2AuthorizationServerConfiguration;

public class OAuth2AuthorizationServerConfigurationTest {
  @Test
  public void oAuth2AuthorizationServerConfigurationShouldBeAnnotated() {
    assertThat(OAuth2AuthorizationServerConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableAuthorizationServer.class);
  }

  @Test
  public void oAuth2AuthorizationServerConfigurationShouldHavePublicConstructor() {
    assertThat(new OAuth2AuthorizationServerConfiguration()).isNotNull();
  }
}