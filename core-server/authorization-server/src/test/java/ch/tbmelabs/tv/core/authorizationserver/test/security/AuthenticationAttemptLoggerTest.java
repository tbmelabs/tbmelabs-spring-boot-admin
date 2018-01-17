package ch.tbmelabs.tv.core.authorizationserver.test.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationAttemptLogger;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;
import ch.tbmelabs.tv.core.authorizationserver.test.utils.testuser.TestUserManager;

@Transactional
public class AuthenticationAttemptLoggerTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String LOGIN_PROCESSING_URL = "/signin";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private TestUserManager testUserManager;

  @Before
  public void beforeTestSetUp() {
    authenticationLogRepository.deleteAll();

    BruteforceFilterService.resetFilter();
  }

  @Test
  public void loginWithInvalidUsernameShouldNotBeRegistered() throws Exception {
    mockMvc.perform(
        post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, "invalid").param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()));

    assertThat(authenticationLogRepository.findAll()).hasSize(0)
        .withFailMessage("The %s should not register bruteforce attempts!", AuthenticationAttemptLogger.class);
  }

  @Test
  public void loginWithValidUsernameAndInvalidPasswordShouldBeRegistered() throws Exception {
    mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUserManager.getUserUser().getUsername())
            .param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()));

    List<AuthenticationLog> logs = (ArrayList<AuthenticationLog>) authenticationLogRepository.findAll();

    assertThat(logs).hasSize(1).withFailMessage("Check that the %s registers failing login attempts of existing users!",
        AuthenticationAttemptLogger.class);
    assertThat(logs).extracting("state").containsExactly(AUTHENTICATION_STATE.NOK.name())
        .withFailMessage("The %s was wrong passed!", AUTHENTICATION_STATE.class);
    assertThat(logs).extracting("user").extracting("username")
        .containsExactly(testUserManager.getUserUser().getUsername())
        .withFailMessage("Wrong %s is linked in the %s!", User.class, AuthenticationLog.class);
  }

  @Test
  public void loginWithValidUserShouldBeRegistered() throws Exception {
    mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUserManager.getUserUser().getUsername())
            .param(PASSWORD_PARAMETER_NAME, testUserManager.getUserUser().getPassword()))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()));

    List<AuthenticationLog> logs = (ArrayList<AuthenticationLog>) authenticationLogRepository.findAll();

    assertThat(logs).hasSize(1).withFailMessage("Check that the %s registers failing login attempts of existing users!",
        AuthenticationAttemptLogger.class);
    assertThat(logs).extracting("state").containsExactly(AUTHENTICATION_STATE.OK.name())
        .withFailMessage("The %s was wrong passed!", AUTHENTICATION_STATE.class);
    assertThat(logs).extracting("user").extracting("username")
        .containsExactly(testUserManager.getUserUser().getUsername())
        .withFailMessage("Wrong %s is linked in the %s!", User.class, AuthenticationLog.class);
  }
}