package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.core.entryserver.configuration.OAuth2SSOZuulProxyConfiguration;
import ch.tbmelabs.tv.core.entryserver.test.ZuulEntryServerTest;

public class OAuth2SSOZuulProxyConfigurationTest implements ZuulEntryServerTest {
  @Test
  public void oauth2SSOZuulProxyConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(Configuration.class)
        .withFailMessage("Annotate " + OAuth2SSOZuulProxyConfiguration.class + " with " + Configuration.class
            + " to make it scannable for the spring application!");

    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(EnableZuulProxy.class)
        .withFailMessage("Annotate " + OAuth2SSOZuulProxyConfiguration.class + " with " + EnableZuulProxy.class
            + " to enable the zuul proxy service!");

    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(EnableOAuth2Sso.class)
        .withFailMessage("Annotate " + OAuth2SSOZuulProxyConfiguration.class + " with " + EnableOAuth2Sso.class
            + " to enable single sign on via authentication server!");
  }
}