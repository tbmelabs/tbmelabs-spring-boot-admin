package ch.tbmelabs.tv.core.authorizationserver.test.service.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.authorizationserver.service.mail.MailService;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.UserMailService;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.EmailConfirmationTokenService;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class UserMailServiceTest {
  private static final String TEST_SENDER_ADDRESS = "no-reply@tbme.tv";
  private static final String TEST_SERVER_ADDRESS = "localhost";
  private static final Integer TEST_SERVER_PORT = 80;
  private static final String TEST_CONTEXT_PATH = "";

  @Mock
  private JavaMailSender mockMailSender;

  @Mock
  private EmailConfirmationTokenService mockEmailConfirmationTokenService;

  @Spy
  @InjectMocks
  private UserMailService fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "senderAddress", TEST_SENDER_ADDRESS);

    ReflectionTestUtils.setField(fixture, "serverAddress", TEST_SERVER_ADDRESS);
    ReflectionTestUtils.setField(fixture, "serverPort", TEST_SERVER_PORT);
    ReflectionTestUtils.setField(fixture, "contextPath", TEST_CONTEXT_PATH);
  }

  @Test
  public void userMailServiceShouldBeAnnotated() {
    assertThat(UserMailService.class).hasAnnotation(Service.class).hasAnnotation(Profile.class);

    assertThat(UserMailService.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly("!" + SpringApplicationProfile.NO_MAIL);
  }

  @Test
  public void userMailServiceShouldExtendMailService() {
    assertThat(MailService.class).isAssignableFrom(UserMailService.class);
  }

  @Test
  public void userMailServiceShouldHavePublicConstructor() {
    assertThat(new UserMailService()).isNotNull();
  }

  // TODO
  // @Test
  // public void sendSignupConfirmationShouldSendMimeMessage() {
  // User user = Mockito.mock(User.class);
  // doReturn(RandomStringUtils.random(11)).when(user).getUsername();
  // doReturn(RandomStringUtils.random(11)).when(user).getEmail();
  //
  // fixture.sendSignupConfirmation(user);
  //
  // verify(mockMailSender, times(1)).send(Mockito.any(MimeMessage.class));
  // }
}