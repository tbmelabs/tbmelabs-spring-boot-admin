package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import ch.tbmelabs.tv.core.authorizationserver.configuration.OAuth2AuthorizationServerConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;

public class OAuth2AuthorizationServerConfigurationTest
    extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  @Test
  public void oAuth2AuthorizationServerConfigurationShouldBeAnnotated() {
    assertThat(OAuth2AuthorizationServerConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!",
        OAuth2AuthorizationServerConfiguration.class, Configuration.class);

    assertThat(OAuth2AuthorizationServerConfiguration.class).hasAnnotation(EnableAuthorizationServer.class)
        .withFailMessage("Annotate %s with %s to enable the authorization server!",
            OAuth2AuthorizationServerConfiguration.class, EnableAuthorizationServer.class);
  }
}