package ch.tbmelabs.tv.shared.constants.test.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.junit.Test;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class SpringApplicationProfileTest {
  public static final String PROD = "prod";
  public static final String DEV = "dev";
  public static final String TEST = "test";
  public static final String ELK = "elk";

  @Test
  public void springApplicationProfilesShouldBePublicStatic() {
    assertThat(SpringApplicationProfile.PROD).isEqualTo(PROD);
    assertThat(SpringApplicationProfile.DEV).isEqualTo(DEV);
    assertThat(SpringApplicationProfile.TEST).isEqualTo(TEST);
    assertThat(SpringApplicationProfile.ELK).isEqualTo(ELK);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessableConstructor() {
    assertThat(Arrays.stream(SpringApplicationProfile.class.getDeclaredConstructors())
        .anyMatch(constructor -> Modifier.isPublic(constructor.getModifiers()))).isFalse();
  }
}