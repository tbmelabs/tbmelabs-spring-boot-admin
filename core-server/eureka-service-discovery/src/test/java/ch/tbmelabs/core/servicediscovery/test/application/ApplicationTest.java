package ch.tbmelabs.core.servicediscovery.test.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.MockitoAnnotations.initMocks;

import ch.tbmelabs.tv.core.servicediscovery.Application;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.util.ReflectionTestUtils;

public class ApplicationTest {

  private static final String PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE =
      "Do not attempt to run an application in productive and development environment at the same time!";

  @Spy
  @InjectMocks
  private static Application fixture;
  private final MockEnvironment mockEnvironment = new MockEnvironment();

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
  public void applicationConstructorShouldAcceptEnvironment() {
    assertThat(new Application(mockEnvironment)).isNotNull();
  }

  @Test
  public void initBeanShouldThrowExceptionIfProductiveAndDevelopmentProfilesAreActive() {
    assertThatThrownBy(() -> fixture.initBean()).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE);
  }
}
