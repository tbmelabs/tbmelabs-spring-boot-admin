package ch.tbmelabs.tv.shared.constants.test.oauth2;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.junit.Test;

import ch.tbmelabs.tv.shared.constants.oauth2.ClientGrantType;

public class ClientGrantTypeTest {
  private static final String CLIENT_GRANT_ERROR_MESSAGE = "Do not attempt to change any %s: \"%s\" is a standardized value!";

  private static final String AUTHORIZATION_CODE = "authorization_code";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final String IMPLICIT = "implicit";
  private static final String PASSWORD = "password";

  public void clientGrantTypesShouldBePublicStatic() {
    assertThat(ClientGrantType.AUTHORIZATION_CODE).isEqualTo(AUTHORIZATION_CODE)
        .withFailMessage(CLIENT_GRANT_ERROR_MESSAGE, ClientGrantType.class, AUTHORIZATION_CODE);
    assertThat(ClientGrantType.REFRESH_TOKEN).isEqualTo(REFRESH_TOKEN).withFailMessage(CLIENT_GRANT_ERROR_MESSAGE,
        ClientGrantType.class, REFRESH_TOKEN);
    assertThat(ClientGrantType.IMPLICIT).isEqualTo(IMPLICIT).withFailMessage(CLIENT_GRANT_ERROR_MESSAGE,
        ClientGrantType.class, IMPLICIT);
    assertThat(ClientGrantType.PASSWORD).isEqualTo(PASSWORD).withFailMessage(CLIENT_GRANT_ERROR_MESSAGE,
        ClientGrantType.class, PASSWORD);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessableConstructor() {
    assertThat(Arrays.stream(ClientGrantType.class.getDeclaredConstructors()).anyMatch(Constructor::isAccessible))
        .isFalse().withFailMessage("%s is a static holder class and should not contain any accessible constructor!",
            ClientGrantType.class);
  }
}