package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;

import ch.tbmelabs.tv.core.authorizationserver.configuration.AuthenticationManagerConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.configuration.TokenServiceConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;

public class AuthenticationManagerConfigurationTest
    extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final String TOKEN_SERVICE_BEAN_NAME = "tokenServiceBean";

  @Autowired
  private AuthenticationManagerConfiguration authenticationManagerConfiguration;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager injectedAuthenticationManager;

  @Test
  public void tokenServiceConfigurationShouldBeAnnotated() {
    assertThat(TokenServiceConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", TokenServiceConfiguration.class,
        Configuration.class);
  }

  @Test
  public void tokenServiceBeanShouldReturnATokenService()
      throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    Method tokenServiceBean = TokenServiceConfiguration.class.getDeclaredMethod(TOKEN_SERVICE_BEAN_NAME,
        new Class[] {});

    assertThat(tokenServiceBean.getDeclaredAnnotation(Bean.class)).isNotNull();
    assertThat(tokenServiceBean.invoke(authenticationManagerConfiguration, new Object[] {}))
        .isEqualTo(injectedAuthenticationManager)
        .withFailMessage("The configured %s should equal the primary registered %s in spring context!",
            TokenService.class, Bean.class);
  }
}