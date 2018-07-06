package ch.tbmelabs.tv.shared.constants.test.security;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.shared.constants.security.UserAuthority;
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
    assertThat(UserAuthority.GANDALF).isEqualTo(GANDALF);
    assertThat(UserAuthority.SERVER_ADMIN).isEqualTo(SERVER_ADMIN);
    assertThat(UserAuthority.SERVER_SUPPORT).isEqualTo(SERVER_SUPPORT);
    assertThat(UserAuthority.CONTENT_ADMIN).isEqualTo(CONTENT_ADMIN);
    assertThat(UserAuthority.CONTENT_SUPPORT).isEqualTo(CONTENT_SUPPORT);
    assertThat(UserAuthority.PREMIUM_USER).isEqualTo(PREMIUM_USER);
    assertThat(UserAuthority.USER).isEqualTo(USER);
    assertThat(UserAuthority.GUEST).isEqualTo(GUEST);
    assertThat(UserAuthority.ANONYMOUS).isEqualTo(ANONYMOUS);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessibleConstructor() throws NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor<UserAuthority> fixture =
        UserAuthority.class.getDeclaredConstructor();
    fixture.setAccessible(true);

    assertThat(Modifier.isPrivate(fixture.getModifiers())).isTrue();
    assertThat(fixture.newInstance()).isNotNull();
  }
}
