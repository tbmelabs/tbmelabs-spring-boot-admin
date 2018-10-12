package ch.tbmelabs.configurationserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.mock.env.MockEnvironment;
import ch.tbmelabs.configurationserver.Application;
import ch.tbmelabs.serverconstants.spring.SpringApplicationProfileEnum;

public class ApplicationTest {

  private static final String PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE =
      "Do not attempt to run an application in productive and development environment at the same time!";

  private MockEnvironment mockEnvironment;

  private Application fixture;

  @Before
  public void beforeTestSetUp() {
    mockEnvironment = new MockEnvironment();

    fixture = new Application(mockEnvironment);
  }

  @Test
  public void shouldBeAnnotated() {
    assertThat(Application.class).hasAnnotation(SpringCloudApplication.class);
  }

  @Test
  public void constructorShouldAcceptEnvironment() {
    assertThat(new Application(mockEnvironment)).isNotNull();
  }

  @Test
  public void postConstructReportsNoErrorIfOnlyDevelopmentProfileIsActive() {
    mockEnvironment.setActiveProfiles(SpringApplicationProfileEnum.DEV.getName());

    fixture.postConstruct();
  }

  @Test
  public void postConstructReportsNoErrorIfOnlyProductiveProfileIsActive() {
    mockEnvironment.setActiveProfiles(SpringApplicationProfileEnum.PROD.getName());

    fixture.postConstruct();
  }

  @Test
  public void postConstructShouldThrowExceptionIfProductiveAndDevelopmentProfilesAreActive() {
    mockEnvironment.setActiveProfiles(SpringApplicationProfileEnum.PROD.getName(),
        SpringApplicationProfileEnum.DEV.getName());

    assertThatThrownBy(() -> fixture.postConstruct()).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(PRODUCTIVE_AND_DEVELOPMENT_ENVIRONMENT_ACTIVE_ERROR_MESSAGE);
  }
}
