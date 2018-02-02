package ch.tbmelabs.tv.core.entryserver.test.security.logout;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.entryserver.security.logout.OAuth2LogoutHandler;

public class OAuth2LogoutHandlerTest {
  private static final String OAUTH2_SERVER_URI = "http://localhost";
  private static final String AUTHORIZATION_SERVER_LOGOUT_ENDPOINT_URL = "http://localhost/logout";

  private static OAuth2LogoutHandler fixture;

  @BeforeClass
  public static void beforeClassSetUp() throws IllegalAccessException, NoSuchFieldException, SecurityException {
    fixture = new OAuth2LogoutHandler();

    Field oauth2ServerUri = OAuth2LogoutHandler.class.getDeclaredField("oauth2ServerUri");
    oauth2ServerUri.setAccessible(true);
    oauth2ServerUri.set(fixture, OAUTH2_SERVER_URI);
  }

  @Test
  public void oauth2LogoutHandlerShouldBeAnnotated() {
    assertThat(OAuth2LogoutHandler.class).hasAnnotation(Component.class);
  }

  @Test
  public void initBeanShouldSetOAuth2LogoutRedirectUrl()
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
    Method initBean = OAuth2LogoutHandler.class.getDeclaredMethod("initBean", new Class<?>[] {});

    assertThat(initBean.isAccessible()).isFalse();

    initBean.setAccessible(true);
    initBean.invoke(fixture, new Object[] {});

    assertThat(fixture).hasFieldOrPropertyWithValue("defaultTargetUrl", AUTHORIZATION_SERVER_LOGOUT_ENDPOINT_URL);
  }
}