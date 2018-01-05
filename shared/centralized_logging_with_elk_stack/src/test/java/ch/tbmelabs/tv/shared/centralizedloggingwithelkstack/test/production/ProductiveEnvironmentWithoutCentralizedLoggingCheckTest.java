package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.production;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.production.ProductiveEnvironmentWithoutCentralizedLoggingCheck;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ProductiveEnvironmentWithoutCentralizedLoggingCheckTest {
  @Test
  public void productiveEnvironmentWithoutCentralizedLoggingCheckShouldBeAnnotated() {
    assertThat(ProductiveEnvironmentWithoutCentralizedLoggingCheck.class).hasAnnotation(Component.class)
        .withFailMessage("Annotate %s with %s to make it scannable for the spring application!",
            ProductiveEnvironmentWithoutCentralizedLoggingCheck.class, Component.class);

    assertThat(ProductiveEnvironmentWithoutCentralizedLoggingCheck.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly(SpringApplicationProfile.PROD).withFailMessage(
            "Annotate %s with %s and value \"%s\" because the check should only apply in productive environments!",
            ProductiveEnvironmentWithoutCentralizedLoggingCheck.class, Profile.class, SpringApplicationProfile.PROD);
  }
}