package ch.tbmelabs.tv.core.entryserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import ch.tbmelabs.tv.core.entryserver.Application;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ApplicationTest {
  private static final String PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE = "Do not attempt to run an application in productive and development environment at the same time!";

  private static Application fixture;

  private static MockEnvironment mockEnvironment;

  @BeforeClass
  public static void beforeClassSetUp() throws IllegalAccessException, NoSuchFieldException, SecurityException {
    fixture = new Application();

    mockEnvironment = new MockEnvironment();
    mockEnvironment.setActiveProfiles(SpringApplicationProfile.PROD, SpringApplicationProfile.DEV);

    Field environment = Application.class.getDeclaredField("environment");
    environment.setAccessible(true);
    environment.set(fixture, mockEnvironment);
  }

  @Test(expected = InvocationTargetException.class)
  public void initBeanShouldThrowExceptionIfProductiveAndDevelopmentProfilesAreActive()
      throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    Method initBean = Application.class.getDeclaredMethod("initBean", new Class<?>[] {});

    assertThat(initBean.isAccessible()).isFalse();

    initBean.setAccessible(true);

    try {
      initBean.invoke(fixture, new Object[] {});
    } catch (Exception e) {
      assertThat(e.getCause()).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getCause().getLocalizedMessage())
          .isEqualTo(PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE);
      throw e;
    }
  }
}