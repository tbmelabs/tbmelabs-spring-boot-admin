package ch.tbmelabs.tv.core.authorizationserver.test.service.mail;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.service.mail.MailService;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class MailServiceTest {
  @Test
  public void mailServiceShouldBeAnnotated() {
    assertThat(MailService.class).hasAnnotation(Service.class).hasAnnotation(Profile.class);

    assertThat(MailService.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly("!" + SpringApplicationProfile.NO_MAIL);
  }

  @Test
  public void mailServiceShouldHavePublicConstructor() {
    assertThat(new MailService()).isNotNull();
  }
}