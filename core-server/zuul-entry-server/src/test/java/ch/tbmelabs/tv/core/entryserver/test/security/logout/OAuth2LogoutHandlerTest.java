package ch.tbmelabs.tv.core.entryserver.test.security.logout;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.entryserver.security.logout.OAuth2LogoutHandler;

public class OAuth2LogoutHandlerTest {
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
}