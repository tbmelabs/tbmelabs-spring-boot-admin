package ch.tbmelabs.configurationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.util.ReflectionTestUtils;
import ch.tbmelabs.configurationserver.configuration.SecurityConfiguration;

public class SecurityConfigurationTest {

  SecurityConfiguration fixture;

  @Before
  public void beforeTestSetup() {
    fixture = new SecurityConfiguration();
  }

  @Test
  public void securityConfigurationShouldBeAnnotated() {
    assertThat(SecurityConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableWebSecurity.class).hasAnnotation(EnableGlobalMethodSecurity.class);
  }

  @Test
  public void enablesGlobalPrePostMethodSecurity() {
    final EnableGlobalMethodSecurity enableGlobalMethodSecurityAnnotation =
        SecurityConfiguration.class.getDeclaredAnnotation(EnableGlobalMethodSecurity.class);

    assertThat(enableGlobalMethodSecurityAnnotation.prePostEnabled()).isTrue();
  }

  @Test
  public void securityConfigurationShouldHavePublicConstructor() {
    assertThat(new SecurityConfiguration()).isNotNull();
  }

  @Test
  public void authorizesAnyRequests() throws Exception {
//    HttpSecurity httpSecuritySpy = Mockito.spy(new HttpSecurity(objectPostProcessor, authenticationBuilder, sharedObjects));

//    ReflectionTestUtils.invokeMethod(fixture, "configure", httpSecuritySpy);

//    verify(httpSecuritySpy, times(1)).authorizeRequests();
  }
}
