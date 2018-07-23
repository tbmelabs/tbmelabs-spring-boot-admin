package ch.tbmelabs.tv.core.authorizationserver.test.service.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.MailService;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.UserMailServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.EmailConfirmationTokenService;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class UserMailServiceImplTest {

  private static final String TEST_SENDER_ADDRESS = "no-reply@tbme.tv";
  private static final String TEST_SERVER_ADDRESS = "localhost";
  private static final Integer TEST_SERVER_PORT = 80;
  private static final String TEST_CONTEXT_PATH = "";

  @Mock
  private EmailConfirmationTokenService mockEmailConfirmationTokenService;

  @Mock
  private JavaMailSender mockMailSender;

  @Spy
  @InjectMocks
  private UserMailServiceImpl fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "senderAddress", TEST_SENDER_ADDRESS);
    ReflectionTestUtils.setField(fixture, "serverAddress", TEST_SERVER_ADDRESS);
    ReflectionTestUtils.setField(fixture, "serverPort", TEST_SERVER_PORT);
    ReflectionTestUtils.setField(fixture, "contextPath", TEST_CONTEXT_PATH);

    doReturn(UUID.randomUUID().toString()).when(mockEmailConfirmationTokenService)
        .createUniqueEmailConfirmationToken(ArgumentMatchers.any(User.class));

    doNothing().when(fixture).sendMail(ArgumentMatchers.any(User.class),
        ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
  }

  @Test
  public void userMailServiceShouldBeAnnotated() {
    assertThat(UserMailServiceImpl.class).hasAnnotation(Service.class).hasAnnotation(Profile.class);

    assertThat(UserMailServiceImpl.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly("!" + SpringApplicationProfile.NO_MAIL);
  }

  @Test
  public void userMailServiceShouldExtendMailService() {
    assertThat(MailService.class).isAssignableFrom(UserMailServiceImpl.class);
  }

  @Test
  public void sendSignupConfirmationShouldCallMailService() {
    User user = new User();
    user.setUsername(RandomStringUtils.random(11));

    fixture.sendSignupConfirmation(user);

    verify(fixture, times(1)).sendMail(ArgumentMatchers.eq(user),
        ArgumentMatchers.eq("Confirm registration to TBME Labs"), ArgumentMatchers.anyString());
  }
}
