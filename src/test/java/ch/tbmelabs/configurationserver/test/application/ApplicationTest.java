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
    mockEnvironment.setActiveProfiles(SpringApplicationProfileEnum.PROD.getName(),
        SpringApplicationProfileEnum.DEV.getName());

    fixture = new Application(mockEnvironment);
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
