package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ch.tbmelabs.tv.core.authorizationserver.configuration.CentralizedLoggingConfiguration;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.annotation.EnableCentralizedLogging;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class CentralizedLoggingConfigurationTest {

  @Test
  public void centralizedLoggingConfigurationShouldBeAnnotated() {
    assertThat(CentralizedLoggingConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableCentralizedLogging.class).hasAnnotation(Profile.class);

    assertThat(CentralizedLoggingConfiguration.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly(SpringApplicationProfile.PROD);
  }

  @Test
  public void centralizedLoggingConfigurationShouldHavePublicConstructr() {
    assertThat(new CentralizedLoggingConfiguration()).isNotNull();
  }
}
