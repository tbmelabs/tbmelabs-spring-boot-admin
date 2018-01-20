package ch.tbmelabs.tv.shared.constants.test.oauth2;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.junit.Test;

import ch.tbmelabs.tv.shared.constants.oauth2.ClientScope;

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
  public void staticHolderClassShouldNotHaveAnyAccessableConstructor() {
    assertThat(Arrays.stream(ClientScope.class.getDeclaredConstructors()).anyMatch(Constructor::isAccessible))
        .isFalse();
  }
}