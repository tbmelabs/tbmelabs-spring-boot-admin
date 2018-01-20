package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.entryserver.configuration.OAuth2SSOZuulProxyConfiguration;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAware;

public class OAuth2SSOZuulProxyConfigurationTest extends AbstractZuulApplicationContextAware {
  @Test
  public void oauth2SSOZuulProxyConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableZuulProxy.class).hasAnnotation(EnableOAuth2Sso.class);
  }
}