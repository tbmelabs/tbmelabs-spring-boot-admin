package ch.tbmelabs.tv.shared.constants.test.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.junit.Test;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class SpringApplicationProfileTest {
  private static final String APPLICATION_PROFILE_ERROR_MESSAGE = "Do not attempt to change any %s: \"%s\" is a standardized value!";

  public static final String PROD = "prod";
  public static final String DEV = "dev";
  public static final String TEST = "test";
  public static final String ELK = "elk";

  @Test
  public void springApplicationProfilesShouldBePublicStatic() {
    assertThat(SpringApplicationProfile.PROD).isEqualTo(PROD).withFailMessage(APPLICATION_PROFILE_ERROR_MESSAGE,
        SpringApplicationProfile.class, PROD);
    assertThat(SpringApplicationProfile.DEV).isEqualTo(DEV).withFailMessage(APPLICATION_PROFILE_ERROR_MESSAGE,
        SpringApplicationProfile.class, DEV);
    assertThat(SpringApplicationProfile.TEST).isEqualTo(TEST).withFailMessage(APPLICATION_PROFILE_ERROR_MESSAGE,
        SpringApplicationProfile.class, TEST);
    assertThat(SpringApplicationProfile.ELK).isEqualTo(ELK).withFailMessage(APPLICATION_PROFILE_ERROR_MESSAGE,
        SpringApplicationProfile.class, ELK);
  }

  @Test
  public void staticHolderClassShouldNotHaveAnyAccessableConstructor() {
    assertThat(
        Arrays.stream(SpringApplicationProfile.class.getDeclaredConstructors()).anyMatch(Constructor::isAccessible))
            .isFalse().withFailMessage("%s is a static holder class and should not contain any accessible constructor!",
                SpringApplicationProfile.class);
  }
}