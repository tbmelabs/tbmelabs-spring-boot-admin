package ch.tbmelabs.tv.core.authorizationserver.test.security.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationServerContextAwareTest;
import ch.tbmelabs.tv.core.authorizationserver.test.domain.dto.UserDTOTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

public class LoginEndpointIntTest extends AbstractOAuth2AuthorizationServerContextAwareTest {

  private static final String LOGIN_PROCESSING_URL = "/login";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  private static final String UNAUTHORIZED_MESSAGE = "Unauthorized";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  private User testUser;
  private String password;

  @Before
  public void beforeTestSetUp() {
    authenticationLogRepository.deleteAll();

    User newUser = UserDTOTest.createTestUser();
    newUser.setIsEnabled(true);
    password = newUser.getPassword();

    testUser = userRepository.save(newUser);
  }

  @Test
  public void loginProcessingWithInvalidUsernameShouldFail() throws Exception {
    String errorMessage = mockMvc
      .perform(post(LOGIN_PROCESSING_URL).with(csrf()).param(USERNAME_PARAMETER_NAME, "invalid")
        .param(PASSWORD_PARAMETER_NAME, password))
      .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn()
      .getResponse().getErrorMessage();

    assertThat(errorMessage).isEqualTo(UNAUTHORIZED_MESSAGE);
  }

  @Test
  public void loginProcessingWithInvalidPasswordShoulFail() throws Exception {
    String errorMessage = mockMvc
      .perform(post(LOGIN_PROCESSING_URL).with(csrf())
        .param(USERNAME_PARAMETER_NAME, testUser.getUsername())
        .param(PASSWORD_PARAMETER_NAME, "invalid"))
      .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn()
      .getResponse().getErrorMessage();

    assertThat(errorMessage).isEqualTo(UNAUTHORIZED_MESSAGE);
  }

  @Test
  public void loginProcessingWithValidCredentialsShouldSucceed() throws Exception {
    String redirectUrl = mockMvc
      .perform(post(LOGIN_PROCESSING_URL).with(csrf())
        .param(USERNAME_PARAMETER_NAME, testUser.getUsername())
        .param(PASSWORD_PARAMETER_NAME, password))
      .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse()
      .getRedirectedUrl();

    assertThat(redirectUrl).isEqualTo("/");
  }

  @Test
  public void loginProcessingShouldFailIfUserIsDisabled() throws Exception {
    testUser.setIsEnabled(false);
    userRepository.save(testUser);

    String errorMessage = mockMvc
      .perform(post(LOGIN_PROCESSING_URL).with(csrf())
        .param(USERNAME_PARAMETER_NAME, testUser.getUsername())
        .param(PASSWORD_PARAMETER_NAME, password))
      .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn()
      .getResponse().getErrorMessage();

    assertThat(errorMessage).isEqualTo(UNAUTHORIZED_MESSAGE);
  }

  @Test
  public void loginProcessingShouldFailIfUserIsBlocked() throws Exception {
    testUser.setIsBlocked(true);
    userRepository.save(testUser);

    String errorMessage = mockMvc
      .perform(post(LOGIN_PROCESSING_URL).with(csrf())
        .param(USERNAME_PARAMETER_NAME, testUser.getUsername())
        .param(PASSWORD_PARAMETER_NAME, password))
      .andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value())).andReturn()
      .getResponse().getErrorMessage();

    assertThat(errorMessage).isEqualTo(UNAUTHORIZED_MESSAGE);
  }
}
