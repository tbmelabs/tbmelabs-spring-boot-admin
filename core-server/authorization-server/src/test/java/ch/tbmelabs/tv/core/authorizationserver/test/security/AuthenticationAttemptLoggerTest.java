package ch.tbmelabs.tv.core.authorizationserver.test.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog;
import ch.tbmelabs.tv.core.authorizationserver.domain.AuthenticationLog.AUTHENTICATION_STATE;
import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationAttemptLogger;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAwareJunitTest;

public class AuthenticationAttemptLoggerTest extends AbstractOAuth2AuthorizationApplicationContextAwareJunitTest {
  private static final String LOGIN_PROCESSING_URL = "/signin";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  private Role testRole;
  private User testUser;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Autowired
  private UserCRUDRepository userRepository;

  @Autowired
  private RoleCRUDRepository authorityRepository;

  @Before
  public void beforeTestSetUp() {
    authenticationLogRepository.deleteAll();
    authorityRepository.deleteAll();
    userRepository.deleteAll();

    testRole = new Role();
    testRole.setName("TEST");

    testUser = new User();
    testUser.setUsername("Testuser");
    testUser.setPassword("Password99$");
    testUser.setConfirmation("Password99$");
    testUser.setEmail("some.test@email.ch");

    authorityRepository.save(testRole);
    userRepository.save(testUser);

    UserRoleAssociation grantedAuthority = new UserRoleAssociation();
    grantedAuthority.setUserId(testUser.getId());
    grantedAuthority.setUser(testUser);
    grantedAuthority.setUserRoleId(testRole.getId());
    grantedAuthority.setUserRole(testRole);

    testUser.setGrantedAuthorities(Arrays.asList(grantedAuthority));

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
    mockMvc.perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUser.getUsername())
        .param(PASSWORD_PARAMETER_NAME, "invalid")).andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()));

    List<AuthenticationLog> logs = (ArrayList<AuthenticationLog>) authenticationLogRepository.findAll();

    assertThat(logs).hasSize(1).withFailMessage("Check that the %s registers failing login attempts of existing users!",
        AuthenticationAttemptLogger.class);
    assertThat(logs).extracting("state").containsExactly(AUTHENTICATION_STATE.NOK.name())
        .withFailMessage("The %s was wrong passed!", AUTHENTICATION_STATE.class);
    assertThat(logs).extracting("user").extracting("username").containsExactly(testUser.getUsername())
        .withFailMessage("Wrong %s is linked in the %s!", User.class, AuthenticationLog.class);
  }

  @Test
  public void loginWithValidUserShouldBeRegistered() throws Exception {
    mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, testUser.getUsername())
            .param(PASSWORD_PARAMETER_NAME, testUser.getConfirmation()))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value()));

    List<AuthenticationLog> logs = (ArrayList<AuthenticationLog>) authenticationLogRepository.findAll();

    assertThat(logs).hasSize(1).withFailMessage("Check that the %s registers failing login attempts of existing users!",
        AuthenticationAttemptLogger.class);
    assertThat(logs).extracting("state").containsExactly(AUTHENTICATION_STATE.OK.name())
        .withFailMessage("The %s was wrong passed!", AUTHENTICATION_STATE.class);
    assertThat(logs).extracting("user").extracting("username").containsExactly(testUser.getUsername())
        .withFailMessage("Wrong %s is linked in the %s!", User.class, AuthenticationLog.class);
  }
}