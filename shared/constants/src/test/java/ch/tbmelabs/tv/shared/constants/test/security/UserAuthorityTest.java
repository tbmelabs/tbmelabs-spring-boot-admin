package ch.tbmelabs.tv.shared.constants.test.security;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.shared.constants.security.UserRole;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.Test;

public class UserAuthorityTest {

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
    assertThat(UserRole.GANDALF).isEqualTo(GANDALF);
    assertThat(UserRole.SERVER_ADMIN).isEqualTo(SERVER_ADMIN);
    assertThat(UserRole.SERVER_SUPPORT).isEqualTo(SERVER_SUPPORT);
    assertThat(UserRole.CONTENT_ADMIN).isEqualTo(CONTENT_ADMIN);
    assertThat(UserRole.CONTENT_SUPPORT).isEqualTo(CONTENT_SUPPORT);
    assertThat(UserRole.PREMIUM_USER).isEqualTo(PREMIUM_USER);
    assertThat(UserRole.USER).isEqualTo(USER);
    assertThat(UserRole.GUEST).isEqualTo(GUEST);
    assertThat(UserRole.ANONYMOUS).isEqualTo(ANONYMOUS);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessibleConstructor() throws NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor<UserRole> fixture =
        UserRole.class.getDeclaredConstructor();
    fixture.setAccessible(true);

    assertThat(Modifier.isPrivate(fixture.getModifiers())).isTrue();
    assertThat(fixture.newInstance()).isNotNull();
  }
}
