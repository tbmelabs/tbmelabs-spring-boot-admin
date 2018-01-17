package ch.tbmelabs.tv.core.authorizationserver.test.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.configuration.SecurityConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationFailureHandler;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testuser.CreateTestUser;

@Transactional
@CreateTestUser(username = "Testuser", email = "some.test@email.ch", password = "Password99$", confirmation = "Password99$")
public class LoginEndpointTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String LOGIN_PROCESSING_URL = "/signin";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  private static final String ERROR_FORWARD_URL = "/signin?error";
  private static final String SUCCESS_FORWARD_URL = "/";

  private static final String VALID_USERNAME = "Testuser";
  private static final String VALID_PASSWORD = "Password99$";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Before
  public void beforeTestSetUp() {
    authenticationLogRepository.deleteAll();

    BruteforceFilterService.resetFilter();
  }

  @Test
  public void loginProcessingWithInvalidUsernameShouldFail() throws Exception {
    String redirectUrl = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, "invalid").param(PASSWORD_PARAMETER_NAME,
            VALID_PASSWORD))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse().getRedirectedUrl();

    assertThat(redirectUrl).isEqualTo(ERROR_FORWARD_URL).withFailMessage(
        "Check that the %s and %s are configured correctly!", SecurityConfiguration.class,
        AuthenticationFailureHandler.class);
  }

  @Test
  public void loginProcessingWithInvalidPasswordShoulFail() throws Exception {
    String redirectUrl = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, VALID_USERNAME)
            .param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse().getRedirectedUrl();

    assertThat(redirectUrl).isEqualTo(ERROR_FORWARD_URL).withFailMessage(
        "Check that the %s and %s are configured correctly!", SecurityConfiguration.class,
        AuthenticationFailureHandler.class);
  }

  @Test
  public void loginProcessingWithValidCredentialsShouldSucceed() throws Exception {
    String redirectUrl = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, VALID_USERNAME)
            .param(PASSWORD_PARAMETER_NAME, VALID_PASSWORD))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse().getRedirectedUrl();

    assertThat(redirectUrl).isEqualTo(SUCCESS_FORWARD_URL).withFailMessage(
        "Check that the %s and %s are configured correctly!", SecurityConfiguration.class,
        AuthenticationFailureHandler.class);
  }
}