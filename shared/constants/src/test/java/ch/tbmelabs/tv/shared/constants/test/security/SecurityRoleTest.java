package ch.tbmelabs.tv.shared.constants.test.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.junit.Test;

import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

public class SecurityRoleTest {
  private static final String SECURITY_ROLE_ERROR_MESSAGE = "Do not attempt to change any %s: \"%s\" is a standardized value!";

  private static final String GANDALF = "GANDALF";
  private static final String SERVER_ADMIN = "SERVER_ADMIN";
  private static final String SERVER_SUPPORT = "SERVER_SUPPORT";
  private static final String CONTENT_ADMIN = "CONTENT_ADMIN";
  private static final String CONTENT_SUPPORT = "CONTENT_SUPPORT";
  private static final String PREMIUM_USER = "PREMIUM_USER";
  private static final String USER = "USER";
  private static final String GUEST = "GUEST";
  private static final String ANONYMOUS = "ANONYMOUS";

  @Test
  public void securityRolesShouldBePublicStatic() {
    assertThat(SecurityRole.GANDALF).isEqualTo(GANDALF).withFailMessage(SECURITY_ROLE_ERROR_MESSAGE, SecurityRole.class,
        GANDALF);
    assertThat(SecurityRole.SERVER_ADMIN).isEqualTo(SERVER_ADMIN).withFailMessage(SECURITY_ROLE_ERROR_MESSAGE,
        SecurityRole.class, SERVER_ADMIN);
    assertThat(SecurityRole.SERVER_SUPPORT).isEqualTo(SERVER_SUPPORT).withFailMessage(SECURITY_ROLE_ERROR_MESSAGE,
        SecurityRole.class, SERVER_SUPPORT);
    assertThat(SecurityRole.CONTENT_ADMIN).isEqualTo(CONTENT_ADMIN).withFailMessage(SECURITY_ROLE_ERROR_MESSAGE,
        SecurityRole.class, CONTENT_ADMIN);
    assertThat(SecurityRole.CONTENT_SUPPORT).isEqualTo(CONTENT_SUPPORT).withFailMessage(SECURITY_ROLE_ERROR_MESSAGE,
        SecurityRole.class, CONTENT_SUPPORT);
    assertThat(SecurityRole.PREMIUM_USER).isEqualTo(PREMIUM_USER).withFailMessage(SECURITY_ROLE_ERROR_MESSAGE,
        SecurityRole.class, PREMIUM_USER);
    assertThat(SecurityRole.USER).isEqualTo(USER).withFailMessage(SECURITY_ROLE_ERROR_MESSAGE, SecurityRole.class,
        USER);
    assertThat(SecurityRole.GUEST).isEqualTo(GUEST).withFailMessage(SECURITY_ROLE_ERROR_MESSAGE, SecurityRole.class,
        GUEST);
    assertThat(SecurityRole.ANONYMOUS).isEqualTo(ANONYMOUS).withFailMessage(SECURITY_ROLE_ERROR_MESSAGE,
        SecurityRole.class, ANONYMOUS);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessableConstructor() {
    assertThat(Arrays.stream(SecurityRole.class.getDeclaredConstructors()).anyMatch(Constructor::isAccessible))
        .isFalse().withFailMessage("%s is a static holder class and should not contain any accessible constructor!",
            SecurityRole.class);
  }
}