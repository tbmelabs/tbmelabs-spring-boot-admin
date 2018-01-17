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
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class AuthenticationManagerConfigurationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String AUTHENTICATION_MANAGER_BEAN_NAME = "authenticationManager";

  @Autowired
  private AuthenticationManagerConfiguration authenticationManagerConfiguration;

  @Autowired
  private AuthenticationManager injectedAuthenticationManager;

  @Test
  public void authenticationManagerConfigurationShouldBeAnnotated() {
    assertThat(AuthenticationManagerConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void authenticationManagerBeanShouldReturnAnAuthenticationManager()
      throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    Method authenticationManagerBean = AuthenticationManagerConfiguration.class
        .getDeclaredMethod(AUTHENTICATION_MANAGER_BEAN_NAME, new Class[] {});

    assertThat(authenticationManagerBean.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(authenticationManagerBean.invoke(authenticationManagerConfiguration, new Object[] {}))
        .isEqualTo(injectedAuthenticationManager);
  }
}