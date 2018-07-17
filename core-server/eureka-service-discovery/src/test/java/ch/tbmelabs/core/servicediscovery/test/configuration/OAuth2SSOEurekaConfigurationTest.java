package ch.tbmelabs.core.servicediscovery.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.core.servicediscovery.configuration.OAuth2SSOSecurityConfiguration;
import org.junit.Test;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

public class OAuth2SSOEurekaConfigurationTest {

  @Test
  public void eurekaConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOSecurityConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableOAuth2Sso.class).hasAnnotation(EnableEurekaServer.class);
  }

  @Test
  public void oAuth2SSOEurekaConfigurationShouldHavePublicConstructor() {
    assertThat(new OAuth2SSOSecurityConfiguration()).isNotNull();
  }
}
