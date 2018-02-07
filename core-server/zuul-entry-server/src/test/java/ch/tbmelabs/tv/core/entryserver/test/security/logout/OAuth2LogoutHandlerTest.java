package ch.tbmelabs.tv.core.entryserver.test.security.logout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.entryserver.security.logout.OAuth2LogoutHandler;

public class OAuth2LogoutHandlerTest {
  private static final String OAUTH2_SERVER_URI = "http://localhost";
  private static final String AUTHORIZATION_SERVER_LOGOUT_ENDPOINT_URL = "http://localhost/logout";

  @Mock
  private OAuth2LogoutHandler fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "oauth2ServerUri", OAUTH2_SERVER_URI);

    doCallRealMethod().when(fixture).initBean();
  }

  @Test
  public void oauth2LogoutHandlerShouldBeAnnotated() {
    assertThat(OAuth2LogoutHandler.class).hasAnnotation(Component.class);
  }

  @Test
  public void oauth2LogoutHandlerShouldExtendsSimpleUrlLogoutSuccessHandler() {
    assertThat(SimpleUrlLogoutSuccessHandler.class).isAssignableFrom(OAuth2LogoutHandler.class);
  }

  @Test
  public void oauth2LogoutHandlerShouldHavePublicConstructor() {
    assertThat(new OAuth2LogoutHandler()).isNotNull();
  }

  @Test
  public void initBeanShouldSetOAuth2LogoutRedirectUrl() {
    fixture.initBean();

    verify(fixture, times(1)).setDefaultTargetUrl(AUTHORIZATION_SERVER_LOGOUT_ENDPOINT_URL);
  }
}