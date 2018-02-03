package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import ch.tbmelabs.tv.core.authorizationserver.configuration.SecurityConfiguration;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class SecurityConfigurationTest {
  @Mock
  private WebSecurity webSecurity;

  @Mock
  private Environment environment;

  @Spy
  @InjectMocks
  private SecurityConfiguration fixture;

  @Before
  public void beforeTestSetUp() throws Exception {
    initMocks(this);

    when(environment.getActiveProfiles()).thenReturn(new String[] { SpringApplicationProfile.DEV });

    doCallRealMethod().when(fixture).init(webSecurity);
  }

  @Test
  public void securityConfigurationShouldBeAnnotated() {
    assertThat(SecurityConfiguration.class).hasAnnotation(Configuration.class).hasAnnotation(EnableWebSecurity.class)
        .hasAnnotation(EnableGlobalMethodSecurity.class);
  }

  @Test
  public void configurationShouldDebugHttpRequestsIfDevelopmentProfileIsActive() throws Exception {
    fixture.configure(webSecurity);

    verify(webSecurity, times(1)).debug(true);
  }
}