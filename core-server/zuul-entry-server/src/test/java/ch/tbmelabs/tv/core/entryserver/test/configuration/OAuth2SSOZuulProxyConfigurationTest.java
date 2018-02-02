package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import ch.tbmelabs.tv.core.entryserver.configuration.OAuth2SSOZuulProxyConfiguration;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class OAuth2SSOZuulProxyConfigurationTest {
  private static OAuth2SSOZuulProxyConfiguration fixture;

  private static MockEnvironment mockEnvironment;

  @BeforeClass
  public static void beforeClassSetUp() throws NoSuchFieldException, SecurityException, IllegalAccessException {
    fixture = new OAuth2SSOZuulProxyConfiguration();

    mockEnvironment = new MockEnvironment();
    mockEnvironment.setActiveProfiles(SpringApplicationProfile.DEV);

    Field environment = OAuth2SSOZuulProxyConfiguration.class.getDeclaredField("environment");
    environment.setAccessible(true);
    environment.set(fixture, mockEnvironment);
  }

  @Test
  public void oauth2SSOZuulProxyConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOZuulProxyConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableZuulProxy.class).hasAnnotation(EnableOAuth2Sso.class);
  }

  @Test
  public void initBeanShouldDebugHttpRequestsIfDevelopmentProfileIsActive()
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
    Method configure = OAuth2SSOZuulProxyConfiguration.class.getDeclaredMethod("configure", WebSecurity.class);

    assertThat(configure.isAccessible()).isFalse();

    WebSecurity testSecurity = new WebSecurity(new ObjectPostProcessor<Object>() {
      @Override
      public <O> O postProcess(O object) {
        return object;
      }
    });

    configure.setAccessible(true);
    configure.invoke(fixture, testSecurity);

    assertThat(testSecurity).hasFieldOrPropertyWithValue("debugEnabled", true);
  }
}