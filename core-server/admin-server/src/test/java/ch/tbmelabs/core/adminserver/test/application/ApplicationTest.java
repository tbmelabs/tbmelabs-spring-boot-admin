package ch.tbmelabs.core.adminserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.adminserver.Application;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ApplicationTest {
  private static final String PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE = "Do not attempt to run an application in productive and development environment at the same time!";

  @Mock
  private Environment mockEnvironment;

  @Spy
  @InjectMocks
  private static Application fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new String[] { SpringApplicationProfile.PROD, SpringApplicationProfile.DEV }).when(mockEnvironment)
        .getActiveProfiles();
  }

  @Test
  public void applicationShouldBeAnnotated() {
    assertThat(Application.class).hasAnnotation(SpringCloudApplication.class);
  }

  @Test
  public void applicationShoudHavePublicConstructor() {
    assertThat(new Application()).isNotNull();
  }

  @Test(expected = IllegalArgumentException.class)
  public void initBeanShouldThrowExceptionIfProductiveAndDevelopmentProfilesAreActive()
      throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {

    try {
      fixture.initBean();
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getLocalizedMessage()).isEqualTo(PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE);
      throw e;
    }
  }

  @Test
  public void publicStaticVoidMainShouldStartSpringApplication() {
    Application.main(new String[] {});
  }

  @Test
  public void configureShouldAddApplicationSourceToApplicationBuilder() {
    SpringApplicationBuilder builder = Mockito.mock(SpringApplicationBuilder.class);

    ReflectionTestUtils.invokeMethod(fixture, "configure", builder);

    verify(builder, times(1)).sources(Application.class);
  }
}