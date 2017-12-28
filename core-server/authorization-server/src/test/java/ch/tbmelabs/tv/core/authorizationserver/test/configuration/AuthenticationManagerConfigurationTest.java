package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import ch.tbmelabs.tv.core.authorizationserver.configuration.AuthenticationManagerConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.configuration.TokenServiceConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;

public class AuthenticationManagerConfigurationTest
    extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final String AUTHENTICATION_MANAGER_BEAN_NAME = "authenticationManager";

  @Autowired
  private AuthenticationManagerConfiguration authenticationManagerConfiguration;

  @Autowired
  private AuthenticationManager injectedAuthenticationManager;

  @Test
  public void authenticationManagerConfigurationShouldBeAnnotated() {
    assertThat(AuthenticationManagerConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", TokenServiceConfiguration.class,
        Configuration.class);
  }

  @Test
  public void authenticationManagerBeanShouldReturnAnAuthenticationManager()
      throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    Method authenticationManagerBean = AuthenticationManagerConfiguration.class
        .getDeclaredMethod(AUTHENTICATION_MANAGER_BEAN_NAME, new Class[] {});

    assertThat(authenticationManagerBean.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(authenticationManagerBean.invoke(authenticationManagerConfiguration, new Object[] {}))
        .isEqualTo(injectedAuthenticationManager)
        .withFailMessage("The configured %s should equal the primary registered %s in spring context!",
            AuthenticationManagerConfiguration.class, Bean.class);
  }
}