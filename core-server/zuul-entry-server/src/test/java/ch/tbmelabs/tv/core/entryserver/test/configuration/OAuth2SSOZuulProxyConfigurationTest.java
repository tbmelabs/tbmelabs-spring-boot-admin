package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.entryserver.configuration.OAuth2SSOZuulProxyConfiguration;
import ch.tbmelabs.tv.core.entryserver.test.AbstractZuulApplicationContextAwareJunitTest;

public class OAuth2SSOZuulProxyConfigurationTest extends AbstractZuulApplicationContextAwareJunitTest {
  @Test
  public void oauth2SSOZuulProxyConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", OAuth2SSOZuulProxyConfiguration.class,
        Configuration.class);

    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(EnableZuulProxy.class).withFailMessage(
        "Annotate %s with %s to enable the zuul proxy service!", OAuth2SSOZuulProxyConfiguration.class,
        EnableZuulProxy.class);

    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(EnableOAuth2Sso.class).withFailMessage(
        "Annotate %s with %s to enable single sign on via authentication server!",
        OAuth2SSOZuulProxyConfiguration.class, EnableOAuth2Sso.class);
  }
}