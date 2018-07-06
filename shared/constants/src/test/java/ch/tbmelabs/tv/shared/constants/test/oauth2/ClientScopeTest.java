package ch.tbmelabs.tv.shared.constants.test.oauth2;

import static org.assertj.core.api.Assertions.assertThat;

import ch.tbmelabs.tv.shared.constants.oauth2.ClientScope;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.Test;

public class ClientScopeTest {

  private static final String READ = "read";
  private static final String WRITE = "write";
  private static final String TRUST = "trust";

  @Test
  public void clientScopesShouldBePublicStatic() {
    assertThat(ClientScope.READ).isEqualTo(READ);
    assertThat(ClientScope.WRITE).isEqualTo(WRITE);
    assertThat(ClientScope.TRUST).isEqualTo(TRUST);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessibleConstructor() throws NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor<ClientScope> fixture = ClientScope.class.getDeclaredConstructor();
    fixture.setAccessible(true);

    assertThat(Modifier.isPrivate(fixture.getModifiers())).isTrue();
    assertThat(fixture.newInstance()).isNotNull();
  }
}
