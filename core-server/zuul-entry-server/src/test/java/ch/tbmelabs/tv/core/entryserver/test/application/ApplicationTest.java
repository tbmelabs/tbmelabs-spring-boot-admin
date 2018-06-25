package ch.tbmelabs.tv.core.entryserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.entryserver.Application;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.util.ReflectionTestUtils;

public class ApplicationTest {

  private static final String PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE =
      "Do not attempt to run an application in productive and development environment at the same time!";

  private final MockEnvironment mockEnvironment = new MockEnvironment();

  @Spy
  @InjectMocks
  private Application fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    mockEnvironment.setActiveProfiles(SpringApplicationProfile.PROD, SpringApplicationProfile.DEV);
    ReflectionTestUtils.setField(fixture, "environment", mockEnvironment);
  }

  @Test
  public void applicationShouldBeAnnotated() {
    assertThat(Application.class).hasAnnotation(SpringCloudApplication.class);
  }

  @Test
  public void applicationShouldHavePublicConstructor() {
    assertThat(new Application()).isNotNull();
  }

  @Test
  public void initBeanShouldThrowExceptionIfProductiveAndDevelopmentProfilesAreActive() {
    assertThatThrownBy(() -> fixture.initBean()).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE);
  }

  @Test
  public void configureShouldAddApplicationSourceToApplicationBuilder() {
    SpringApplicationBuilder builder = Mockito.mock(SpringApplicationBuilder.class);

    ReflectionTestUtils.invokeMethod(fixture, "configure", builder);

    verify(builder, times(1)).sources(Application.class);
  }
}
